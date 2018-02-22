package com.cts.interview.folders;

import java.util.List;

/**
 * Created by James on 22/02/2018.
 */
public class IDFolder {

    private String id;
    private String name;
    private List<String> parentIds;

    public IDFolder() {}

    public IDFolder(String id, String name, List<String> parentIds) {
        this.id = id;
        this.name = name;
        this.parentIds = parentIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getParentIds() {
        return parentIds;
    }

    public void setParentIds(List<String> parentIds) {
        this.parentIds = parentIds;
    }
}
