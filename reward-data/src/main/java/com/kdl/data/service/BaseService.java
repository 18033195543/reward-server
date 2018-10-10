package com.kdl.data.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor=Exception.class)
public class BaseService {

		protected Logger logger = LoggerFactory.getLogger(getClass());
}
