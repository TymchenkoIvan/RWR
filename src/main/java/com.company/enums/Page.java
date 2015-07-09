package com.company.enums;

public enum Page { INDEX("index"), ADD("add"), INFO("candidateInfo");

    private String address;

    Page(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
