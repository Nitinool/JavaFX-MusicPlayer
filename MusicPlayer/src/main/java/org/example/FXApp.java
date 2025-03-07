package org.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.io.FilenameFilter;




public class FXApp extends Application {

    // 提示字符 每次操作后输出提示字符
    private Label status = new Label();

    //歌曲列表
    private ObservableList<String> songList = FXCollections.observableArrayList();
    private MediaPlayer mediaPlayer;

    //各种button
    private Button selectFolderButton = new Button("Select Song Folder");
    private Button playButton = new Button("Play");
    private Button pauseButton = new Button("Pause");
    private Button stopButton = new Button("Stop");

    @Override
    public void start(Stage primaryStage) throws Exception {

        selectFolderButton.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File songFolder = directoryChooser.showDialog(primaryStage);
            if (songFolder != null) {
                // 定义文件过滤器
                FilenameFilter mp3Filter = (dir, name) -> name.endsWith(".mp3");
                File[] songs = songFolder.listFiles(mp3Filter);
                if (songs != null && songs.length > 0) {
                    // 清空之前的歌曲列表
                    songList.clear();
                    status.setText("SongList Found");
                    // 将歌曲名称添加到ObservableList
                    for (File song : songs) {
                        songList.add(song.getName());
                    }
                } else {
                    status.setText("No Songs Found");
                }
            }
        });

        // 创建ListView并绑定到songList
        ListView<String> songListView = new ListView<>();
        songListView.setItems(songList);

        playButton.setOnAction(e -> {
            mediaPlayer.play();
        });
        pauseButton.setOnAction(e -> {
            mediaPlayer.pause();
        });
        stopButton.setOnAction(e -> {
            mediaPlayer.stop();
        });

        //处理用户点击歌曲事件
        songListView.setOnMouseClicked(e -> {
            String selectedsong = songListView.getSelectionModel().getSelectedItem();
        });

        // 创建布局并添加控件
        VBox root = new VBox();
        root.getChildren().addAll(selectFolderButton, songListView,status);

        // 设置场景
        Scene scene = new Scene(root, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Song List App");
        primaryStage.show();
    }



}