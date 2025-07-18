package com.SearchFileTool;

import com.Thread.test.ThreadPool.MyThreadPool;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class SearchFileTool {
    private final SearchModel model;
    private final String SearchFiles;
    private String TargetName;
    private final boolean useThread;
    private MyThreadPool myThreadPool;
    int count = 0;
    public SearchFileTool(SearchModel model, String SearchFiles,  boolean useThread) {
        this.model = model;
        this.SearchFiles = SearchFiles;
        this.useThread = useThread;
    }
    public SearchFileTool(SearchModel model, boolean  useThread) {
        this.model = model;
        this.useThread = useThread;
        this.SearchFiles = System.getProperty("user.dir");
    }

    private void locateTarget_exact(File file) {
        if (!file.exists() || file.isFile()) {
            return;
        }
        File[] files = file.listFiles();
        if (files == null) return;
        for (File f : files) {
            if (f.getAbsolutePath().equals("/proc/") || f.getAbsolutePath().equals("/sys/")) continue;
            if (f.isFile() && f.getName().equals(TargetName)) {
                System.out.println("Found: " + f.getAbsolutePath());
                count++;
                return;
            }
            if (f.isDirectory()) {
                locateTarget_exact(f);
            }
        }
    }

    private void locateTarget_fuzzy(File file) {
        if (!file.exists() || file.isFile()) return;
        File[] files = file.listFiles();
        if (files == null) return;
        for (File f : files) {
            if (f.getAbsolutePath().equals("/proc/") || f.getAbsolutePath().equals("/sys/")) continue;
            if (f.isFile() && f.getName().contains(TargetName)) {
                System.out.println("Found: " + f.getAbsolutePath());
                count++;
                return;
            }
            if (f.isDirectory()) locateTarget_fuzzy(f);
        }
    }

    private class DirTask implements Runnable {
        private final Path dir;
        DirTask(Path dir) { this.dir = dir; }

        @Override
        public void run() {
            try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir)) {
                for (Path p : ds) {
                    if (p.toAbsolutePath().toString().equals("/proc") || p.toAbsolutePath().toString().equals("sys")) continue;
                    if (Files.isDirectory(p)) {
                        myThreadPool.execute(new DirTask(p)); // 提交子目录任务
                    } else if (p.getFileName().toString().contains(TargetName)) {
                        count++;
                        System.out.println("Found: " + p.toAbsolutePath());
                    }
                }
            } catch (IOException e) {
                // 忽略无法访问的目录
            }
        }
    }


    private void EasySearch(String TargetName) {
        this.count = 0;
        this.TargetName = TargetName;
        File f = new File(SearchFiles);
        if (model == SearchModel.exact) locateTarget_exact(f);
        else if (model == SearchModel.fuzzy) locateTarget_fuzzy(f);
    }

    private void ThreadSearch(String TargetName) throws InterruptedException {
        this.count = 0;
        this.TargetName = TargetName;
        this.myThreadPool = new MyThreadPool(6, 8, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10000000));
        myThreadPool.execute(new DirTask(Paths.get(SearchFiles)));
        myThreadPool.awaitTermination(60, TimeUnit.SECONDS);
        myThreadPool.shutdown();
    }

    public void Search(String TargetName) throws InterruptedException {
        if (useThread) ThreadSearch(TargetName);
        else EasySearch(TargetName);
        System.out.println("搜索 " + TargetName + " 找到 " + count + " 个结果");
    }
}
