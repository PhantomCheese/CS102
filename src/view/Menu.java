package view;

import User.User;
import controller.GameController;
import model.ChessColor;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Menu extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    private ChessGameFrame frame;
    JLabel userInfoline;
    ArrayList<User> userList = new ArrayList<>();


    public Menu(int width, int height, ChessGameFrame frame) {
        this.WIDTH = width;
        this.HEIGHT = height;
        setSize(width, height);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        this.frame = frame;
        updateUserList();
        addStartButton();
        addTitle();
        addRegisterButton();
        addLoginButton();
        addUserInfo();
        addRankingListButton();
    }

    public void addStartButton() {
        JButton button = new JButton("Start");
        button.setLocation(WIDTH / 2 - 100, HEIGHT / 2 - 60);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            System.out.println("Click start");
            this.setVisible(false);
            frame.setVisible(true);
        });
    }

    public void addRegisterButton() {
        JButton button = new JButton("New Account");
        button.setLocation(WIDTH / 2 - 100, HEIGHT / 2 - 200);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            System.out.println("Add a new account");
            String userName = JOptionPane.showInputDialog("输入用户名");
            if (userName.length() == 0) {
                JOptionPane.showMessageDialog(null, "用户名不能为空！");
                return;
            }
            try {
                List<String> users = Files.readAllLines(Path.of("resource/UserList"));
                for (String user : users) {
                    String[] userInfo = user.split("/");
                    if (userInfo[0].equals(userName)) {
                        JOptionPane.showMessageDialog(null, "用户名已存在！");
                        return;
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            File file = new File("resource/UserList");
            try {
                FileWriter fw = new FileWriter(file, true);
                fw.write(userName + "/0/\n");
                JOptionPane.showMessageDialog(null, "注册成功");
                userList.add(new User(userName,0));
                fw.close();
            } catch (IOException a) {
                a.printStackTrace();
            }
        });
    }

    public void addLoginButton() {
        JButton button = new JButton("Login");
        button.setLocation(WIDTH / 2 - 100, HEIGHT / 2 - 130);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            String userName = JOptionPane.showInputDialog("输入用户名");
            if (userName.length() != 0) {
                if (userList.size() != 0) {


                    for (int i = 0; i < userList.size(); i++) {

                        if (userList.get(i).getName().equals(userName)) {
                            userInfoline.setText(String.format("User: %-20s  Score: %-15d rank: %d", userList.get(i).getName(), userList.get(i).getScore(), i + 1));
                            JOptionPane.showMessageDialog(null, "登陆成功");
                            String[] options = {"白色", "黑色"};
                            if (JOptionPane.showOptionDialog(null, "选择棋子颜色", "棋子选择对话框",
                                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]) == 0) {
                                userList.get(i).setColor(ChessColor.WHITE);
                            } else userList.get(i).setColor(ChessColor.BLACK);

                            frame.gameController.chessboard.currentPlayer = userList.get(i);
                            frame.gameController.chessboard.userArrayList = userList;


                            frame.userLabel.setText(userList.get(i).getName() + " :" + userList.get(i).getColor());
                            return;
                        }

                    }
                    JOptionPane.showMessageDialog(null, "用户不存在！");
                }
            }


        });
    }

    public void addTitle() {
        JLabel title = new JLabel("Chess", JLabel.CENTER);
        title.setLocation(WIDTH / 2 - 100, HEIGHT / 2 - 300);
        title.setSize(200, 60);
        title.setFont(new Font("Rockwell", Font.BOLD, 30));

        add(title);
    }

    public void addUserInfo() {
        JLabel showUser = new JLabel(" ", JLabel.CENTER);
        userInfoline = showUser;
        showUser.setLocation(WIDTH / 2 - 350, HEIGHT / 2 + 100);
        showUser.setSize(800, 60);
        showUser.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(showUser);
    }

    public void addRankingListButton() {
        JButton button = new JButton("Ranking List");
        button.setLocation(WIDTH / 2 - 100, HEIGHT / 2 + 10);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            System.out.println("Show ranking list");
            StringBuilder sb = new StringBuilder("Rank-----Name------Score----\n\n");
            for (int i = 0; i < userList.size(); i++) {
                sb.append(String.format("%-15s%-16s%-15s\n\n", i + 1, userList.get(i).getName(), userList.get(i).getScore()));
            }
            JOptionPane.showMessageDialog(null, sb);
        });
    }


    public void updateUserList() {
        try {
            List<String> users = Files.readAllLines(Path.of("resource/UserList"));
            ArrayList<User> newList= new ArrayList<>();
            for (String user : users) {
                String[] userInfo = user.split("/");
                newList.add(new User(userInfo[0], Long.parseLong(userInfo[1])));
            }
            //排序
            for (int k = 0; k < 50; k++) {
                for (int i = 0; i < newList.size() - 1; i++) {
                    if (newList.get(i).getScore() < newList.get(i + 1).getScore()) {
                        User u = newList.get(i);
                        newList.set(i, newList.get(i + 1));
                        newList.set(i + 1, u);
                    }
                }
            }
            userList=newList;
            System.out.println("update");
            for (int i = 0; i < userList.size(); i++) {
                System.out.println(userList.get(i).getName());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void autoSave() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < userList.size(); i++) {
            sb.append(userList.get(i).getName()).append("/").append(userList.get(i).score).append("/").append("\n");
        }
        File file = new File("resource/UserList");
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(sb.toString());
            fw.close();
        } catch (IOException a) {
            a.printStackTrace();
        }
    }

}
