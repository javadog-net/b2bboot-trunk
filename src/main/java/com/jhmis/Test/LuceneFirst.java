package com.jhmis.Test;

import com.jhmis.common.utils.FileUtils;
import com.jhmis.common.utils.io.FileUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.awt.*;
import java.io.File;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.Test
 * @Author: hdx
 * @CreateTime: 2020-01-16 16:54
 * @Description: Lucene
 */
public class LuceneFirst {
    public static void main(String[] args) throws Exception {
        //还余引本旅形丝低益
        Directory directory = FSDirectory.open(new File("E:\\workspace\\Lucene"));
//2、基于Directory对系创递一Indexwriter对象

        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_34);
        IndexWriter indexwriter = new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_34, analyzer));
//3、读取础盘上的文件，对应每个文伴创建一个文档对象。
        File dir = new File("E:\\公司相关");
        File[] files = dir.listFiles();
        for (File f : files) {
            //我文件名
            String fileName = f.getName();
            //文产的路证
            String filepath = f.getPath();
            //文件的内容
            //String content = FileUtils.readFileToString(f, "utf-8");
            //文件大小
            long fileSize = FileUtils.sizeOf(f);
            Field fieldName = new Field("name", fileName,
                    Field.Store.YES, Field.Index.NOT_ANALYZED);
            Field fieldpath = new Field("filepath", filepath,
                    Field.Store.YES, Field.Index.NOT_ANALYZED);
            //Field fieldContent = new Field("fieldContent", content, Field.Store.YES, Field.Index.NOT_ANALYZED);
            //创建对象
            Document document = new Document();
            document.add(fieldName);
            document.add(fieldpath);
            //document.add(fieldContent);
            indexwriter.addDocument(document);
            //indexwriter.close();
            if (1 == 1) {

            }
        }


    }
}
