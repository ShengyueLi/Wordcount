package cs181;
import java.io.IOException;
import java.util.HashSet;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Word Count Mapper 
 * Receives lines of text, splits each line into words, and generates key, value pairs. Where 
 * the key is the word, and the value is just 1. The counts for a given key will be aggregated in the reducer. 
 *
 * @param  Raw text
 * @return < Key , 1 >
 * 
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	private final IntWritable one = new IntWritable(1);
	private Text word = new Text();
	private String pattern = "^[a-z][a-z0-9]*$";
	private HashSet<String> punctuations = new HashSet<String>();
	private HashSet<String> StopWords = new HashSet<String>();

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		punctuations.add(".");
		punctuations.add(";");
		punctuations.add(",");
		punctuations.add("'");
		punctuations.add("?");
		punctuations.add(":");
		punctuations.add("-");
		punctuations.add("!");
		punctuations.add("-");
		punctuations.add("(");
		punctuations.add(")");
		
		StopWords.add("I");
		StopWords.add("me");
		StopWords.add("you");
		StopWords.add("your");
		StopWords.add("he");
		StopWords.add("she");
		StopWords.add("his");
		StopWords.add("her");
		StopWords.add("it");
		StopWords.add("its");
		
		StopWords.add("are");
		StopWords.add("is");
		StopWords.add("be");
		StopWords.add("am");
		StopWords.add("was");
		StopWords.add("were");
		StopWords.add("will");

		StopWords.add("for");
		StopWords.add("by");
		StopWords.add("at");
		StopWords.add("from");
		StopWords.add("in");
		StopWords.add("about");
		StopWords.add("to");
		StopWords.add("as");
		StopWords.add("with");
		StopWords.add("before");
		StopWords.add("after");
	
	
		StopWords.add("an");
		StopWords.add("a");
		StopWords.add("the");

		StopWords.add("how");	
		StopWords.add("that");
		StopWords.add("this");
		StopWords.add("to");
	
	
		String line = value.toString(); /* get line of text from variable 'value' and convert to string */

		/* Lets use a string tokenizer to split line by words using a pattern matcher */
		StringTokenizer tokenizer = new StringTokenizer(line);

		while (tokenizer.hasMoreTokens()) {
			word.set(tokenizer.nextToken());
			String stringWord = word.toString().toLowerCase();

			/* for each word, output the word as the key, and value as 1 */
			if (stringWord.matches(pattern) && punctuations.contains(pattern) && StopWords.contains(pattern)) {
				context.write(new Text(stringWord), one);
			}

		}
	}
}