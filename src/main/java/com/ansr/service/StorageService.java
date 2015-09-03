package com.ansr.service;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSFile;

@Service
public class StorageService {
	 @Autowired
	 GridFsOperations gridOperations;
 

    public String save(InputStream inputStream, String contentType, String filename, String id) {
    	DBObject metaData = new BasicDBObject();
    	metaData.put("userId", id);
    	GridFSFile f = gridOperations.store(inputStream, filename, contentType, metaData);
    	return f.getId().toString();
    }
 

/*    public GridFSDBFile get(String id) {
        return gridFs.findOne(new ObjectId(id));
    }
 

    public GridFSDBFile getByFilename(String filename) {
        return gridFs.findOne(filename);
    }*/
}
