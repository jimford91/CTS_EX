package com.cts.interview.folders;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by James on 22/02/2018.
 */
public class PathFolder {

    private String folderName;
    private String folderPath;
    private PathFolder parentFolder;
    private Map<String, PathFolder> childFolders;

    public PathFolder() {}

    public PathFolder(String folderName) {
        this.folderName = folderName;
    }

    public PathFolder(String folderName, String folderPath) {
        this.folderName = folderName;
        this.folderPath = folderPath;
    }

    public PathFolder(String folderName, PathFolder parentFolder) {
        this.folderName = folderName;
        this.parentFolder = parentFolder;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public PathFolder getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(PathFolder parentFolder) {
        this.parentFolder = parentFolder;
    }

    public Map<String, PathFolder> getChildFolders() {
        return childFolders;
    }

    public void setChildFolders(Map<String, PathFolder> childFolders) {
        this.childFolders = childFolders;
    }

    public void addChildFolder(PathFolder pathFolder) {

        if (childFolders == null) {
            childFolders = new HashMap<>();
        }

        childFolders.put(pathFolder.getFolderName(), pathFolder);
    }
}
