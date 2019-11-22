/*
 * Copyright (c) 2019. Created by Harvey Huang.
 * It is not allowed to use the project in any course.
 * All rights reserved.
 */

package main.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FileIO {
    private static final String username = "huang107";
    private static final String host = "bowen.students.cs.ubc.ca";
    private static final String password = "Hh@12345";
    private static final int port = 22;

    private String filePath;

    public FileIO(String filePath) {
        this.filePath = filePath;
    }

    public List<AbstractAccount> readTxt() {
//        downloadFile();
        List<AbstractAccount> accounts = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            String username = "";
            String password = "";
            String nickname = "";
            boolean isUser = true;
            List<String> posts = new ArrayList<>();
            for (String line: lines) {
                if ((line.equals("")) && !(username.equals(""))) {
                    AbstractAccount account = isUser? new UserAccount(username, nickname, password) : new SponsorAccount(username, nickname, password);
                    accounts.add(account);
                    for (String content: posts) {
                        account.publishMoment(content);
                    }
                    username = "";
                    nickname = "";
                    password = "";
                    isUser = true;
                    posts = new ArrayList<>();
                } else if (username.equals("")) {
                    String[] words = line.split(" ");
                    username = words[0];
                    if (words[1].equals("sponsor"))
                        isUser = false;
                } else if (nickname.equals("")) {
                    nickname = line;
                } else if (password.equals("")) {
                    password = line;
                } else {
                    posts.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public List<AbstractAccount> read() {
        List<AbstractAccount> accounts = new ArrayList<>();
        File file = new File("./data/users.json");
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            JSONObject jasonObject = new JSONObject(sb.toString());
            JSONArray usersArray = jasonObject.getJSONArray("users");
            parseUsersJSONArray(accounts, usersArray);
            parseFriends(accounts, usersArray);
        } catch (IOException e) {
            System.out.println("Read fails");
        }
        return accounts;
    }

    private void parseUsersJSONArray(List<AbstractAccount> accounts, JSONArray usersArray) {
        int length = usersArray.length();
        for (int i = 0; i < length; ++ i) {
            JSONObject userObject = usersArray.getJSONObject(i);
            String username = userObject.getString("username");
            String nickname = userObject.getString("nickname");
            String password = userObject.getString("password");
            boolean isUser = userObject.getBoolean("isUser");
            AbstractAccount account;
            if (isUser) {
                account = new UserAccount(username, nickname, password);
            } else {
                account = new SponsorAccount(username, nickname, password);
            }
            JSONArray momentsArray = userObject.getJSONArray("moments");
            account.setMoments(parseMomentsJSONArray(momentsArray, account));
            accounts.add(account);
        }
    }

    private void parseFriends(List<AbstractAccount> accounts, JSONArray usersArray) {
        int length = usersArray.length();
        for (int i = 0; i < length; ++ i) {
            JSONObject userObject = usersArray.getJSONObject(i);
            AbstractAccount account = accounts.get(i);
            JSONArray friendsArray = userObject.getJSONArray("friends");
            parseFriendsFJSONArray(account, accounts, friendsArray);
        }
    }

    //TODO:
    private void parseFriendsFJSONArray(AbstractAccount account, List<AbstractAccount> accounts, JSONArray friendArray) {
        int length = friendArray.length();
        for (int i = 0; i < length; ++ i) {
            JSONObject friendObject = friendArray.getJSONObject(i);
            String username = friendObject.getString("username");
            account.addFriend(findAccount(accounts, username));
        }
    }

    private AbstractAccount findAccount(List<AbstractAccount> accounts, String username) {
        for (AbstractAccount account: accounts) {
            if (username.equals(account.getUsername())) {
                return account;
            }
        }
        return null;
    }

    private List<Moment> parseMomentsJSONArray(JSONArray momentsArray, AbstractAccount account) {
        int length = momentsArray.length();
        List<Moment> moments = new ArrayList<>();
        for (int i = 0; i < length; ++ i) {
            JSONObject momentObject = momentsArray.getJSONObject(i);
            String content = momentObject.getString("content");
            Moment moment = new Moment(content, account);
            moments.add(moment);
        }
        return moments;
    }

    public void write(List<AbstractAccount> accounts) {
        String result = "{\n";
        result += "\"users\": ";
        List<String> usersString = new ArrayList<>();
        for (AbstractAccount user: accounts) {
            usersString.add(userToJSONString(user));
        }
        result += makeJSONArrayString(usersString);
        result += "\n}";
        try {
            PrintWriter writer = new PrintWriter("./data/users.json", "UTF-8");
            writer.println(result);
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            //
        }
    }

    private String makeJSONArrayString(List<String> stringList) {
        String result = "[\n";
        for (String element: stringList) {
            if (stringList.indexOf(element) != stringList.size() - 1)
                result += element + ",\n";
            else {
                result += element + "\n";
            }
        }
        result += "]\n";
        return result;
    }

    private String makeJSONObjectString(List<String> stringList) {
        String result = "{\n";
        for (String element: stringList) {
            if (stringList.indexOf(element) != stringList.size() - 1) {
                result += element + ",\n";
            } else {
                result += element + "\n";
            }
        }
        result += "}\n";
        return result;
    }

    private String userToJSONString(AbstractAccount account) {
        List<String> stringList = new ArrayList<>();
        stringList.add("\"username\": \"" + account.getUsername() + "\"");
        stringList.add("\"nickname\": \"" + account.getNickname() + "\"");
        stringList.add("\"password\": \"" + account.getPassword() + "\"");
        stringList.add("\"isUser\": " + (account instanceof UserAccount));
        stringList.add("\"friends\": " + friendsToJSONArray(account.getFriends()));
        stringList.add("\"moments\": " + momentsToJSONArray(account.getMoments()));
        return makeJSONObjectString(stringList);
    }

    private String friendsToJSONArray(Collection<AbstractAccount> friends) {
        List<String> stringList = new ArrayList<>();
        for (AbstractAccount account: friends) {
            stringList.add("{\n \"username\": \""+ account.getUsername() +"\"\n }\n");
        }
        return makeJSONArrayString(stringList);
    }

    private String momentsToJSONArray(List<Moment> moments) {
        List<String> stringList = new ArrayList<>();
        for (Moment moment: moments) {
            stringList.add(momentToJSONString(moment));
        }
        return makeJSONArrayString(stringList);
    }

    private String momentToJSONString(Moment moment) {
        List<String> stringList = new ArrayList<>();
        stringList.add("\"content\": \"" + moment.getContent() + "\"");
        stringList.add("\"list\": " + friendsToJSONArray(moment.getLikeList()));
        return makeJSONObjectString(stringList);
    }

    public void writeToTxt(List<AbstractAccount> accounts) {
        try {
            PrintWriter writer = new PrintWriter(filePath,"UTF-8");
            for (AbstractAccount account: accounts) {
                boolean isUser = account instanceof UserAccount;
                writer.println(account.getUsername() + " " + (isUser? "user" : "sponsor"));
                writer.println(account.getNickname());
                writer.println(account.getPassword());
                for (Moment moment: account.getMoments()) {
                    writer.println(moment.getContent());
                }
                writer.println();
            }
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

//    private void downloadFile() {
//        try {
//            JSch jSch = new JSch();
//            Session session = jSch.getSession(username, host, port);
//            session.setPassword(password);
//            session.setConfig("StrictHostKeyChecking", "no");
//            session.connect();
//            Channel channel = session.openChannel("sftp");
//            channel.connect();
//            ChannelSftp sftp = (ChannelSftp) channel;
//            sftp.get("/h6f1b/test.txt", "./data/test.txt");
//            channel.disconnect();
//            session.disconnect();
//        } catch (JSchException e) {
//            System.out.println("Connection fails");
//        } catch (SftpException e) {
//            System.out.println("download fails");
//        }
//    }
}
