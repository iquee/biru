package com.luiztaira.service;

import com.luiztaira.domain.NextIdSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceGeneratorService {

	private MongoOperations mOp;

	@Autowired
	public SequenceGeneratorService(MongoOperations mop) {
		this.mOp = mop;
	}

	public long generateSequence(String seqName) {
		NextIdSequence counter = mOp.findAndModify(query(where("_id").is(seqName)), new Update().inc("seq", 1),
				options().returnNew(true).upsert(true), NextIdSequence.class);
		return !Objects.isNull(counter) ? counter.getSeq() : 1;
	}
}
