package com.luiztaira.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.luiztaira.domain.Pdv;
/**
 * Listener to create sequence generator for id's
 * 
 * @author taira
 *
 */
@Component
public class PdvListener extends AbstractMongoEventListener<Pdv> {

	private SequenceGeneratorService sequenceGenerator;

	@Autowired
	public PdvListener(SequenceGeneratorService sequenceGenerator) {
		this.sequenceGenerator = sequenceGenerator;
	}

	@Override
	public void onBeforeConvert(BeforeConvertEvent<Pdv> event) {
		if (event.getSource().getId() < 1) {
			event.getSource().setId(sequenceGenerator.generateSequence(Pdv.SEQUENCE_NAME));
		}
	}

}