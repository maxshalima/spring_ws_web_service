package by.test.someveryfaststorage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import ru.blogic.test.storage.Storage;

public class FastFileStorage implements Storage {

	public static final String PATH_PARAM = "dictPath";
	private File rootFolder;

	public FastFileStorage() throws Exception {
	}

	public void init() throws Exception {
		this.rootFolder = new File(System.getProperty("dictPath"));
		fillData();
		assert ((this.rootFolder.exists()) && (this.rootFolder.isDirectory()));
	}

	private void fillData() throws IOException {
		File rootFolder = new File(System.getProperty("dictPath"));
		if ((rootFolder.exists()) && (rootFolder.list().length > 0)) {
			return;
		}
		rootFolder.mkdirs();

		Random rnd = new Random();
		for (int i = 1; i < 10; i++) {
			File dictDir = new File(rootFolder.getAbsolutePath()
					+ File.separator + "Dict" + i);
			dictDir.mkdir();
			for (int j = 1; j < rnd.nextInt(1024); j++) {
				FileWriter fw = new FileWriter(dictDir + File.separator
						+ UUID.randomUUID().toString());
				fw.write(String.valueOf(rnd.nextLong() % 1000L));
				fw.close();
			}
		}
	}

	public List<String> getDictionariesNames() {
		List<String> names = new ArrayList<String>();
		File[] dictionaries = this.rootFolder.listFiles(new FileFilter() {
			public boolean accept(File child) {
				return child.isDirectory();
			}
		});
		for (File d : dictionaries) {
			names.add(d.getName());
		}
		return names;
	}

	public Storage.Dictionary getDictionary(String name) {
		FileDictionary fileDict = new FileDictionary(new File(
				this.rootFolder.getAbsolutePath() + File.separator + name));

		return fileDict;
	}

	public class FileDictionary implements Storage.Dictionary {
		private File dir;

		public FileDictionary(File dir) {
			this.dir = dir;
		}

		public String getName() {
			return this.dir.getName();
		}

		public int getRecordCount() {
			return this.dir.listFiles().length;
		}

		public Storage.Dictionary.Record getRecord(String id) throws Exception {
			File f = new File(this.dir.getAbsolutePath() + File.separator + id);
			assert (f.exists());
			return new FileRecord(f);
		}

		public String addRecord(String content) throws Exception {
			String id = UUID.randomUUID().toString();
			FileWriter fw = new FileWriter(this.dir + File.separator + id);
			fw.write(content);
			fw.close();
			return id;
		}

		public List<String> getAllRecordsIds() throws Exception {
			List<String> ids = new ArrayList<String>();
			for (File f : this.dir.listFiles()) {
				ids.add(f.getName());
			}
			return ids;
		}

		public class FileRecord implements Storage.Dictionary.Record {
			private File file;

			public FileRecord(File file) {
				this.file = file;
			}

			public String getId() {
				return this.file.getName();
			}

			public Map<Storage.Dictionary.RecordAttr, Object> getAttributes() {
				Map<Storage.Dictionary.RecordAttr, Object> attrs = new HashMap<Storage.Dictionary.RecordAttr, Object>();
				attrs.put(Storage.Dictionary.RecordAttr.ID, this.file.getName());
				attrs.put(Storage.Dictionary.RecordAttr.PATH,
						this.file.getAbsolutePath());
				attrs.put(Storage.Dictionary.RecordAttr.SIZE,
						Long.valueOf(this.file.length()));

				StringBuilder sb = new StringBuilder();
				try {
					BufferedReader br = new BufferedReader(new FileReader(
							this.file));
					while (br.ready()) {
						sb.append(br.readLine());
					}
					br.close();
				} catch (Exception ex) {
				} finally {
					attrs.put(Storage.Dictionary.RecordAttr.CONTENT,
							sb.toString());
				}
				return attrs;
			}

			public String toString() {
				return "["
						+ getAttributes().get(Storage.Dictionary.RecordAttr.ID)
						+ ","
						+ getAttributes().get(
								Storage.Dictionary.RecordAttr.PATH)
						+ ","
						+ getAttributes().get(
								Storage.Dictionary.RecordAttr.SIZE)
						+ ","
						+ getAttributes().get(
								Storage.Dictionary.RecordAttr.CONTENT) + "]";
			}
		}
	}
}
