package org.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class FXApp extends Application {

    private Stage primaryStage;
    private Label statusLabel;
    private File songFolder;
    private ObservableList<String> songList;
    private ListView<String> songListView;
    private Media media;
    private MediaPlayer mediaPlayer;
    private Slider volumeSlider;
    private Button playButton;
    private Button pauseButton;

    public FXApp() {
        this.primaryStage = new Stage();
        this.statusLabel = new Label();
        this.songFolder = null;
        this.songList = FXCollections.observableArrayList();
        this.songListView = new ListView<>(songList);
        this.volumeSlider = new Slider(0, 1, 0.5);

    }

    @Override
    public void start(Stage stage) throws Exception {

        primaryStage.setScene(createScene());
        primaryStage.setTitle("MusicPlayer");
        primaryStage.show();

        initializeControllers();
    }

    private Scene createScene() {
        VBox mainLayout  = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.getChildren().addAll(
                topPanel(),
                songListView,
                bottomPanel()
        );
        return new Scene(mainLayout,600,400);
    };

    private HBox topPanel (){
        //设置选择文件夹事件
        statusLabel.setText("Ready to select a folder");
        Button selectSongFolder = new Button("Select Song Folder");
        selectSongFolder.setOnAction(e -> {
            loadSongs();
        });

        HBox topPanel = new HBox(20);
        topPanel.setPadding(new Insets(20));
        topPanel.getChildren().addAll(
                selectSongFolder,
                statusLabel
        );

        return topPanel;
    }


    private HBox bottomPanel (){
        playButton = new Button("Play");
        pauseButton = new Button("Pause");

        HBox bottomPanel = new HBox(20);
        bottomPanel.setPadding(new Insets(20));
        bottomPanel.getChildren().addAll(
                playButton,
                pauseButton,
                volumeSlider
        );

        // 直接在创建面板时绑定事件处理器
        playButton.setOnAction(e -> playSong());
//        pauseButton.setOnAction(e -> pause());

        return bottomPanel;
    }

    private void loadSongs() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        songFolder = directoryChooser.showDialog(primaryStage);

        if (songFolder == null) {
            statusLabel.setText("No folder selected");
            return;
        }

        songList.clear();

        File[] files = songFolder.listFiles((dir, name) -> name.endsWith(".mp3"));
        if (files != null && files.length > 0) {
            for (File file : files) {
                songList.add(file.getName());
            }
            statusLabel.setText(files.length + " " + "Songs Loaded");
        }else statusLabel.setText("No Songs Found");

        songListView.setItems(songList);
    }

    private void initializeControllers() {
        // 双击播放
        songListView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                playSong();
            }
        });

        volumeSlider.valueProperty().bindBidirectional(volumeSlider.valueProperty());

    }

    private void playSong() {
        //双击歌曲播放 同时停止上一首歌曲
        String songName = songListView.getSelectionModel().getSelectedItem();
        if (songName == null) {
            statusLabel.setText("No song selected");
            return;
        }

        stopCurrentPlayer(); // 停止当前播放

        File songFile = new File(songFolder, songName);
        Media media = new Media(songFile.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(volumeSlider.getValue());
        mediaPlayer.play();
    }

    private void stopCurrentPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }

//    private void pause() {
//        if (mediaPlayer != null && mediaPlayer.is()) {
//            mediaPlayer.pause();
//        }
//    }


//    @Override
//    public void start(Stage primaryStage) throws Exception {
//
//        this.primaryStage = primaryStage;
//        Button selectFolderButton = new Button("Select Song Folder");
//
//
//        //存储音乐根目录到songlist
//        selectFolderButton.setOnAction(e -> {
//            loadSongs();
//        });
//
//        //从songListView处理用户点击歌曲事件 更新一个songmedia和mediaPlayer
//        songListView.setOnMouseClicked(e -> {
//            selectSong();
//        });
//
//
//        // 歌曲播放信息
//        Label songName = new Label("song name");
//        ProgressBar progressBar = new ProgressBar();
//        Button playButton = new Button("Play");
//        Button pauseButton = new Button("Pause");
//
//        // 播放控制
//        playButton.setOnAction(e -> mediaPlayer.play());
//        pauseButton.setOnAction(e -> mediaPlayer.pause());
//
//
//        // 创建布局并添加控件
//        //5 是子节点之间的间距（单位为像素)
//        HBox bottomPanel = new HBox(5, songName, progressBar, playButton, pauseButton,volumeSlider);
//        //new Insets(10) 表示在 HBox 的上下左右各设置 10 像素的内边距。
//        bottomPanel.setPadding(new Insets(10));
//
//        HBox selectPanel = new HBox(10, selectFolderButton, status);
//        selectPanel.setPadding(new Insets(10));
//
//        VBox mainLayout = new VBox(10, selectPanel,songListView, bottomPanel);
//        mainLayout.setPadding(new Insets(20));
//
//        // 设置场景
//        Scene scene = new Scene(mainLayout, 600, 400);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Song List App");
//        primaryStage.show();
//    }
//
//    private void loadSongs(){
//        // 创建一个目录选择框 用户可以选择一个文件夹
//        DirectoryChooser directoryChooser = new DirectoryChooser();
//
//        //showDialog返回一个File对象 表示用户当前选择的目录 如果取消 则为null
//        File folder  = directoryChooser.showDialog(primaryStage);
//
//        if (folder  != null) {
//            songFolder = folder;
//            System.out.println("Selected Song Folder: " + songFolder.getAbsolutePath());
//            songList.clear();
//            // 加载歌曲文件
//            File[] songs = folder.listFiles((dir, name) -> name.endsWith(".mp3"));
//            if (songs != null && songs.length > 0) {
//                // 将歌曲名称添加到ObservableList
//                for (File song : songs) {
//                    songList.add(song.getName());
//                }
//                status.setText(songs.length + " songs Found");
//            } else {
//                status.setText("No Songs Found");
//            }
//
//            songListView.setItems(songList);
//        }
//    }
//
//    private void selectSong(){
//        //从歌曲列表中得到一个歌曲media
//        String selectedsong = songListView.getSelectionModel().getSelectedItem();
//        if (selectedsong != null) {
//            //得到歌曲的路径
//            File songFile = new File(songFolder,selectedsong);
//            //创建媒体并播放
//            songmedia = new Media(songFile.toURI().toString());
//            mediaPlayer = new MediaPlayer(songmedia);
//            mediaPlayer.volumeProperty().bind(volumeSlider.valueProperty());
//
//        }
//    }

}
