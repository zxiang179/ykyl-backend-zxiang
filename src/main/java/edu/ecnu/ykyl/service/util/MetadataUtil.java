package edu.ecnu.ykyl.service.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import edu.ecnu.ykyl.model.QuestionRepository;

/*
 * 解析CSV文件，并保存到数据库 
 */
@Component
public class MetadataUtil {

	private static final Logger LOG = LoggerFactory
			.getLogger(MetadataUtil.class);

	// CSV文件路径
	private static final String CSV_QUESTION = "metadata/questions.csv";

	@Autowired
	private QuestionRepository questionRepository;

	public void load() {
		// 将解析后的对象保存到数据库
		questionRepository.save(parse(CSV_QUESTION, "parseQuestion"));

	}

	@SuppressWarnings("unchecked")
	// 将表格数据存入List对象中
	public List parse(String file, String method) {
		MetadataUtil parser = new MetadataUtil();
		// 查找resource
		ClassPathResource resource = new ClassPathResource(file);
		if (!resource.exists()) {
			LOG.error("NOT found {}", file);
		}

		List result = new ArrayList<>();

		try {
			File tFile = resource.getFile();
			FileInputStream fileInputStream = new FileInputStream(tFile);
			InputStreamReader inputStreamReader = new InputStreamReader(
					fileInputStream);
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			// ignore first line
			String line = bufferedReader.readLine();
			while ((line = bufferedReader.readLine()) != null) {
				// 去除空格
				line = line.replaceAll("\\s*", "");
				// 以逗号划分单元格内容
				String[] values = line.split(",");
				// 将一行的数据添加到result中
				result.add(parser.getClass()
						.getMethod(method, values.getClass())
						.invoke(parser, (Object) values));
			}

		} catch (Exception e) {
			LOG.error("{}", e);
		}
		return result;
	}

	/*
	 * public Question parseQuestion(String[] splits){ Question question = new
	 * Question(); question.setTest(splits[0]); question.setOp1(splits[1]);
	 * question.setOp2(splits[2]); question.setOp3(splits[3]);
	 * question.setOp4(splits[4]); return question; }
	 */

}
