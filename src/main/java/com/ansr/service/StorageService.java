package com.ansr.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
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
 

/*    public GridFSDBFile get(String userId) {
        return gridFs.findOne(new ObjectId(id));
    }*/
 

    public List<GridFSDBFile> getFilesByUser(String userId) {
      List<GridFSDBFile> files =  gridOperations.find(new Query().addCriteria(Criteria.where("metadata.userId").is(userId)));
      return files;
    }
    
    public GridFSDBFile getFileByUserAndName(String userId, String name) {
    	Query q = new Query();
    	q.addCriteria(Criteria.where("metadata.userId").is(userId));
    	q.addCriteria(Criteria.where("filename").is(name));
        GridFSDBFile file =  gridOperations.findOne(q);
        return file;
    }


	public List<String> getFilesNameByUser(String userId) {
		List<GridFSDBFile> files = getFilesByUser(userId);
		List<String> filesName = new ArrayList<String>();
		for(GridFSDBFile f : files) {
			filesName.add(f.getFilename());
		}
		return filesName;
	}
}
