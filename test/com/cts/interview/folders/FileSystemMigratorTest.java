package com.cts.interview.folders;

import com.cts.interview.folders.FileSystemMigrator;
import com.cts.interview.folders.IDFolder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by James on 22/02/2018.
 */
public class FileSystemMigratorTest {

    private FileSystemMigrator migrator;


    @Before
    public void before() {
        migrator = new FileSystemMigrator();
    }

    @Test
    public void testSingleFolderWithParentRoot() {
        IDFolder folder = new IDFolder("abc", "def", Arrays.asList("root"));

        Assert.assertEquals("/def", migrator.migrateFolders(buildFolderArray(folder)).iterator().next());
    }

    @Test
    public void testSingleNestedFolderInOrder() {
        IDFolder folder = new IDFolder("a", "top", Arrays.asList("root"));
        IDFolder nestedFolder = new IDFolder("b", "nested", Arrays.asList("a"));

        Set<String> paths = migrator.migrateFolders(buildFolderArray(folder, nestedFolder));

        Assert.assertEquals(2, paths.size());
        Assert.assertTrue(paths.containsAll(Arrays.asList("/top", "/top/nested")));
    }

    @Test
    public void testSingleNestedFolderNotInOrder() {
        IDFolder folder = new IDFolder("a", "top", Arrays.asList("root"));
        IDFolder nestedFolder = new IDFolder("b", "nested", Arrays.asList("a"));

        Set<String> paths = migrator.migrateFolders(buildFolderArray(nestedFolder, folder));

        Assert.assertEquals(2, paths.size());
        Assert.assertTrue(paths.containsAll(Arrays.asList("/top", "/top/nested")));
    }

    @Test
    public void testDoubleNestedFolderNotInOrder() {
        IDFolder folder = new IDFolder("a", "top", Arrays.asList("root"));
        IDFolder nestedFolder = new IDFolder("b", "nested", Arrays.asList("a"));
        IDFolder nested2Folder = new IDFolder("c", "nested123", Arrays.asList("a"));
        IDFolder nestedNestedFolder = new IDFolder("c", "nestedNested", Arrays.asList("b"));

        Set<String> paths = migrator.migrateFolders(buildFolderArray(nestedFolder, folder, nested2Folder,
                nestedNestedFolder));

        Assert.assertEquals(4, paths.size());
        Assert.assertTrue(paths.containsAll(Arrays.asList("/top", "/top/nested", "/top/nested/nestedNested",
                "/top/nested123")));
    }

    @Test
    public void testSameNameRoot() {
        IDFolder folder = new IDFolder("a", "top", Arrays.asList("root"));
        IDFolder folder1 = new IDFolder("b", "top", Arrays.asList("root"));
        IDFolder folder2 = new IDFolder("c", "top", Arrays.asList("root"));

        Set<String> paths = migrator.migrateFolders(buildFolderArray(folder1, folder, folder2));
        System.out.print(paths);
        Assert.assertEquals(3, paths.size());
        Assert.assertTrue(paths.containsAll(Arrays.asList("/top", "/top_1", "/top_2")));
    }

    @Test
    public void testSameNameRoot2() {
        IDFolder folder = new IDFolder("a", "top", Arrays.asList("root"));
        IDFolder folder1 = new IDFolder("b", "top", Arrays.asList("root"));
        IDFolder folder2 = new IDFolder("c", "top1", Arrays.asList("root"));

        Set<String> paths = migrator.migrateFolders(buildFolderArray(folder1, folder, folder2));
        System.out.print(paths);
        Assert.assertEquals(3, paths.size());
        Assert.assertTrue(paths.containsAll(Arrays.asList("/top", "/top_1", "/top1")));
    }

    @Test
    public void testSameNameRoot3() {
        IDFolder folder = new IDFolder("a", "top", Arrays.asList("root"));
        IDFolder folder1 = new IDFolder("b", "top", Arrays.asList("root"));
        IDFolder folder2 = new IDFolder("c", "top_1", Arrays.asList("root"));

        Set<String> paths = migrator.migrateFolders(buildFolderArray(folder1, folder, folder2));
        System.out.print(paths);
        Assert.assertEquals(3, paths.size());
        Assert.assertTrue(paths.containsAll(Arrays.asList("/top", "/top_1", "/top_1_1")));
    }

    @Test
    public void testMultipleParents() {
        IDFolder folder = new IDFolder("a", "top", Arrays.asList("root"));
        IDFolder folder1 = new IDFolder("b", "top1", Arrays.asList("root"));
        IDFolder folder2 = new IDFolder("c", "nestedA", Arrays.asList("a"));
        IDFolder folder3 = new IDFolder("d", "nestedBoth", Arrays.asList("root", "a"));

        Set<String> paths = migrator.migrateFolders(buildFolderArray(folder1, folder, folder2, folder3));
        System.out.print(paths);
        Assert.assertEquals(4, paths.size());
        Assert.assertTrue(paths.containsAll(Arrays.asList("/top", "/top1", "/top/nestedA", "/nestedBoth")));
    }


    private IDFolder[] buildFolderArray(IDFolder... folders) {
        return folders;
    }
}
