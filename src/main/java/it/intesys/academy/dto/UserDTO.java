package it.intesys.academy.dto;

import java.util.ArrayList;

public class UserDTO {
    private int id;

    private String name;

    private String surname;

    private ArrayList<Integer> projectsId = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public ArrayList<Integer> getProjectsId() {
        return projectsId;
    }

    public void setProjectsId(ArrayList<Integer> projectsId) {
        this.projectsId = projectsId;
    }
    public void addProjectId(Integer projectid) {
        projectsId.add(projectid);
    }
}
