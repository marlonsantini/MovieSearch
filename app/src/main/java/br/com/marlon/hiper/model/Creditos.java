package br.com.marlon.hiper.model;

/**
 * Created by Marlon on 30/10/2017.
 */

public class Creditos {

    private String job;
    private String name;
    private String profile_path;
    private String department;

    public Creditos(){

    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
