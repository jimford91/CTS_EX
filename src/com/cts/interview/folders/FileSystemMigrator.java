package com.cts.interview.folders;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by James on 22/02/2018.
 *
 * With the truncation aspect  of it...
 *
 * You would store the data slightly differently and then wait until the end to build the paths from the mappings
 * of parents to children.
 *
 * If a name was to be truncated down to 400 chars then you would look to go from the lowest file level and shorten the
 * names as much as possible while still keeping them unique, moving up the file structure until the length was correct.
 *
 * You wouldn't need to send any changes to the children, as their path's aren't built until the end.
 *
 * If you could no longer shorten the url without creating duplicates then an error would be thrown.  *
 */
public class FileSystemMigrator {

    private final static PathFolder  rootFolder = new PathFolder("root", "/");
    private Map<String, PathFolder> pathFolderIds;
    private Map<String, PathFolder> pathFolderPaths;
    private Map<String, Integer> pathRenames;

    public FileSystemMigrator() {
        pathFolderIds = new HashMap<>();
        pathFolderPaths = new HashMap<>();
        pathRenames = new HashMap<>();
    }

    public Set<String> migrateFolders(IDFolder[] idFolders) {

        List<IDFolder> parentsNotFound = new ArrayList<>();

        pathFolderIds.put("root", rootFolder);

        for (IDFolder idFolder : idFolders) {
            if (!evaluateFolder(idFolder)) {
                parentsNotFound.add(idFolder);
            }
        }

        boolean allProcessed = parentsNotFound.isEmpty();

        while (!allProcessed) {

            Iterator<IDFolder> iterator = parentsNotFound.iterator();

            while(iterator.hasNext()) {

                IDFolder idFolder = iterator.next();

                if (evaluateFolder(idFolder)) {
                    iterator.remove();
                }
            }
            allProcessed = parentsNotFound.isEmpty();
        }

        return pathFolderPaths.keySet();
    }

    private boolean evaluateFolder(IDFolder idFolder) {

        List<String> parentIds = idFolder.getParentIds();

        if (!pathFolderIds.keySet().containsAll(parentIds)) {
            return false;
        }

        String parentId = locateParent(idFolder);

        PathFolder pathFolder = convertFolder(idFolder, parentId);
        String path = pathFolder.getFolderPath();

        while (pathFolderPaths.containsKey(path)) {
            path = rename(pathFolder.getFolderPath());
        }

        pathFolder.setFolderPath(path);

        pathFolderPaths.put(path, pathFolder);
        return true;
    }

    private PathFolder convertFolder(IDFolder idFolder, String parentId) {

        PathFolder pathFolder = new PathFolder(idFolder.getName());

        PathFolder parent = pathFolderIds.get(parentId);
        pathFolder.setParentFolder(parent);
        parent.addChildFolder(pathFolder);
        pathFolderIds.put(idFolder.getId(), pathFolder);

        String prefix;
        if (parent.equals(rootFolder)) {
            prefix = parent.getFolderPath();
        } else {
            prefix = parent.getFolderPath() + "/";
        }

        pathFolder.setFolderPath(prefix + pathFolder.getFolderName());

        return pathFolder;
    }

    private String rename(String origPath) {

        Integer newCount;
        if (pathRenames.containsKey(origPath)) {
            int renameCount = pathRenames.get(origPath);
            newCount = renameCount + 1;

        } else {
            newCount = 1;
        }

        pathRenames.put(origPath, newCount);
        return origPath + "_" + String.valueOf(newCount);
    }

    // Instead of replicating the folders and creating duplicate structures, I have decided to take the highest
    // folder in the structure. If multiple are at the same level, the one sorted first is found.
    private String locateParent(IDFolder idFolder) {
        return idFolder.getParentIds().stream().sorted(Comparator.comparing(
                parent -> pathFolderIds.get(parent).getFolderPath().replaceAll("[^/]","").length()
            )).findFirst().get();
    }
}
