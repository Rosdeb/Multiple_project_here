package com.example.daraz.searchitem;


    public class User {
        private String login;
        private String avatarUrl;
        private String htmlUrl;

        public User(String login, String avatarUrl,String htmlUrl) {
            this.login = login;
            this.avatarUrl = avatarUrl;
            this.htmlUrl=htmlUrl;

        }

        public String getLogin() {
            return login;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }
        public String getHtmlUrl() {
            return htmlUrl;
        }

    }




