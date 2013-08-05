package mail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LengthTransferTest {
  public static void main(String args[]) throws IOException {

		File inFile = new File("E:\\input.txt");// 输入文件
		BufferedReader reader = new BufferedReader(new FileReader(inFile));
		File ouFile = new File("E:\\output.txt");// 输出文件
		BufferedWriter writer = new BufferedWriter(new FileWriter(ouFile));
		String tempString = "";
		Map<String, String> danweiMap = new HashMap<String, String>();
		List<String> number = null;// 被操作的字符串组
		writer.write("liujunhao225@163.com\r\n\r\n");
		while ((tempString = reader.readLine()) != null) {// 每次读取一行
			if ("".equals(tempString.trim())) {
				continue;// 如果是空行，读取下一行
			}
			number = new LinkedList<String>();

			// 如果这一行是单位换算，把标准单位放到map中
			if (tempString.contains("=")) {

				String danwei[] = tempString.split(" ");
				danweiMap.put(danwei[1], danwei[3]);// danwei[1]
				// 是单位，danwei[3]是换算成meter的长度
				continue;
			} else {

				String operation[] = tempString.split(" ");// 按空格分隔字符串
				int i = 0;
				for (String s : operation) {
					s = manyTurnToOne(s);// 如果是单位并且是复数，转换成标准单位

					// 如果是单位，将单位转换成米（start）
					if (danweiMap.containsKey(s)) {
						double dou1 = Double.parseDouble(number.get(i - 1));// 把数字转成double
						BigDecimal big1 = BigDecimal.valueOf(dou1);// double转成bigDecimal
						double dou2 = Double.parseDouble(danweiMap.get(s));
						BigDecimal big2 = BigDecimal.valueOf(dou2);
						BigDecimal result = BigDecimal.valueOf(0);
						result = big1.multiply(big2);
						number.set(i - 1, String.valueOf(result));
						// 如果是单位，将单位转换成米（start）
					} else {
						// 如果是普通数字，就放到list中去
						number.add(s);
						i++;
					}

				}
				// 如果这个list的size大于2 ，就说明还有没有处理完的
				while (number.size() > 2) {
					double no1 = Double.parseDouble(number.get(0));
					BigDecimal big1 = BigDecimal.valueOf(no1);
					double no2 = Double.parseDouble(number.get(2));
					BigDecimal big2 = BigDecimal.valueOf(no2);
					String op = number.get(1);
					BigDecimal result = BigDecimal.valueOf(0);
					// 加法操作
					if ("+".equals(op)) {
						result = big1.add(big2);
						
						number.remove(2);
						number.remove(1);

						number.set(0, String.valueOf(result));
						continue;
					} else if ("-".equals(op)) { // 减法操作
						result = big1.subtract(big2);
					
						number.remove(2);
						number.remove(1);

						number.set(0, String.valueOf(result));
						continue;
					} else {
						number.set(0, "operator is not include?");// 如果是其它字符就不处理
						break;
					}
				}
				
				BigDecimal bigDecimal =new BigDecimal(number.get(0)).setScale(2, BigDecimal.ROUND_HALF_UP);//将最后结果保留两位小数，四舍五入
			
				writer.write(bigDecimal.toString() + " meter\r\n");
			}

		}
		try {

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				reader.close();// 关闭读入流
			if (writer != null) {
				writer.close();// 关闭输出流
			}
		}
	}
/**
 * 复数转单数
 * @param s
 * @return
 */
	private static String manyTurnToOne(String s) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("miles", "mile");
		map.put("yards", "yard");
		map.put("inches", "inch");
		map.put("feet", "foot");
		map.put("faths", "fath");
		map.put("furlongs", "furlong");
		map.put("mile", "mile");
		map.put("yard", "yard");
		map.put("inch", "inch");
		map.put("foot", "foot");
		map.put("fath", "fath");
		map.put("furlong", "furlong");
		if (map.containsKey(s))
			s = map.get(s);
		return s;
	}

}
