package jp.ac.jaist.DataCrawler;

import jp.ac.jaist.DataCrawler.controller.Crawler;
import jp.ac.jaist.DataCrawler.util.Path;

import java.io.IOException;

/**
 * Created by d on 5/21/2016.
 */
public class Main {
    public static void main(String... agrs) throws Exception {
        init();
        Crawler crawler = new Crawler();
        crawler.run();
//        Analyzer analyzer = new Analyzer();
//        analyzer.run();

    }
    public static void init() throws IOException {
        Path.buildRoot();
    }
}