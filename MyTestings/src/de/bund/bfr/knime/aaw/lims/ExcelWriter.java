package de.bund.bfr.knime.aaw.lims;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {

	XSSFWorkbook workbook;
	XSSFSheet sheet;
	XSSFCellStyle defaultStyle;

	public ExcelWriter() {
		// Blank workbook
		workbook = new XSSFWorkbook();
		// Create a blank sheet
		sheet = workbook.createSheet("default");
		defaultStyle = workbook.createCellStyle();
		defaultStyle.setWrapText(true);
		defaultStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	}

	public ExcelWriter(LinkedHashSet<List<Object>> data) {
		this();
		Footer footer = sheet.getFooter();
		footer.setLeft("BfR, FG 43, Epidemiologie, Zoonosen und Antibiotikaresistenz");
		Header header = sheet.getHeader();
		header.setLeft("Zoonosen-Stichprobenplan 2017 Anhang 11\nVerteilung der Proben auf die Länder");
		header.setCenter("Nationale Prävalenzschätzung");
		header.setRight(new SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis()));
		Set<Integer> dateStyles = new HashSet<Integer>();
		// Iterate over data and write to sheet
		int rownum = 0;
		for (List<Object> rowData : data) {
			XSSFRow row = sheet.createRow(rownum++);
			int cellnum = 0;
			for (Object obj : rowData) {
				XSSFCell cell = row.createCell(cellnum);
				cell.setCellStyle(defaultStyle);
				if (obj instanceof String) {
					String str = (String) obj;
					int index = str.indexOf('^');
					if (index > 0 && index < str.length() - 1) {
						str = str.substring(0, index) + str.substring(index + 1);
						XSSFRichTextString richString = new XSSFRichTextString(str);
						XSSFFont font = workbook.createFont();
						font.setTypeOffset(XSSFFont.SS_SUPER);
						font.setBold(true);
						richString.applyFont(index, str.length(), font);
						cell.setCellValue(richString);
					} else {
						cell.setCellValue(str);
					}
				} else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
				else if (obj instanceof Double)
					cell.setCellValue((Double) obj);
				else if (obj instanceof Boolean)
					cell.setCellValue((Boolean) obj);
				else if (obj instanceof Calendar) {
					cell.setCellValue((Calendar) obj);
					if (!dateStyles.contains(cellnum))
						dateStyles.add(cellnum);
				} else
					System.err.println("Unsupported type: " + obj);

				cellnum++;
			}
		}
		String format = "dd.mm.yyyy";
		for (int i : dateStyles) {
			setStyle(null, null, i, i, false, false, false, false, false, format, null);
		}
	}

	public XSSFRow createRow(int rowNum) {
		return sheet.createRow(rowNum);
	}

	public void save(String filename) {
		try {
			FileOutputStream out = new FileOutputStream(new File(filename));
			workbook.write(out);
			out.close();
			System.out.println(filename + " written successfully on disk.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setStyle(Integer rowStart, Integer rowEnd, Integer colStart, Integer colEnd, boolean isBold,
			boolean isCenter, boolean isRight, boolean hasRightBorder, boolean hasBottomBorder,
			String dataFormat, Color color) {
		setStyle(rowStart, rowEnd, colStart, colEnd, isBold, isCenter, isRight, hasRightBorder, hasBottomBorder, dataFormat, color, null, null);
	}
	public void setStyle(Integer rowStart, Integer rowEnd, Integer colStart, Integer colEnd, boolean isBold,
			boolean isCenter, boolean isRight, boolean hasRightBorder, boolean hasBottomBorder,
			String dataFormat, Color color, Integer colMergeStart, Integer colMergeCount) {
		XSSFFont font = workbook.createFont();
		if (isBold)
			font.setBold(isBold);

		XSSFCellStyle style = (XSSFCellStyle) defaultStyle.clone();
		if (color != null) {
			//style.setFillBackgroundColor(new XSSFColor(color));
			style.setFillForegroundColor(new XSSFColor(color));
			style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			//style.setFillPattern(XSSFCellStyle.FINE_DOTS);
		}
		if (isRight) style.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
		else if (isCenter) style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style.setFont(font);

		if (hasRightBorder)
			style.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
		if (hasBottomBorder)
			style.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);

		if (dataFormat != null) {
			XSSFCreationHelper createHelper = workbook.getCreationHelper();
			style.setDataFormat(createHelper.createDataFormat().getFormat(
					dataFormat));
		}
				
		for (int i = (rowStart == null ? 0 : rowStart); i <= (rowEnd == null ? sheet.getLastRowNum() : rowEnd); i++) {
			XSSFRow row = sheet.getRow(i);
			if (row != null) {
				for (int j = (colStart == null ? row.getFirstCellNum() : colStart); j <= (colEnd == null ? row.getLastCellNum() : colEnd); j++) {
					XSSFCell cell = row.getCell(j);
					setS(cell, style);
				}
			}
		}
		
		// merge cells
		if (rowStart != null && rowEnd != null && colMergeStart != null && colMergeCount != null) {
			for (int i=rowStart;i<=rowEnd;i++) {
				for (int j=colMergeStart;j<sheet.getRow(i).getLastCellNum();j+=colMergeCount) {
					sheet.addMergedRegion(new CellRangeAddress(i,i,j,j+colMergeCount-1));											
				}
			}
		}
	}
	public void setFormat4Cols(Integer rowStart, Integer rowEnd, Integer colStart, Integer colDistance, String dataFormat) {
		if (colStart != null && colDistance != null && dataFormat != null) {
			XSSFCreationHelper createHelper = workbook.getCreationHelper();
			short s = createHelper.createDataFormat().getFormat(dataFormat);
			
			if (rowStart == null) rowStart = 0;
			if (rowEnd == null) rowEnd = sheet.getLastRowNum();
			for (int i=rowStart;i<=rowEnd;i++) {
				XSSFRow row = sheet.getRow(i);
				if (row != null) {
					for (int j=colStart;j<row.getLastCellNum();j+=colDistance) {
						XSSFCell cell = row.getCell(j);
						if (cell != null) {
							XSSFCellStyle cstyle = (XSSFCellStyle) cell.getCellStyle().clone();
							cstyle.setDataFormat(s);
							cell.setCellStyle(cstyle);
						}
					}
				}
			}
		}
	}

	private void setS(XSSFCell cell, XSSFCellStyle style) {
		if (cell != null) {
			XSSFCellStyle cstyle = (XSSFCellStyle) cell.getCellStyle().clone();
			if (cstyle.equals(defaultStyle)) {
				cell.setCellStyle(style);
			} else {
				if (style.getFont().getBold() != defaultStyle.getFont()
						.getBold())
					cstyle.setFont(style.getFont());
				if (style.getAlignment() != defaultStyle.getAlignment())
					cstyle.setAlignment(style.getAlignment());
				if (style.getBorderRight() != defaultStyle.getBorderRight())
					cstyle.setBorderRight(style.getBorderRight());
				if (style.getBorderBottom() != defaultStyle.getBorderBottom())
					cstyle.setBorderBottom(style.getBorderBottom());
				if (style.getDataFormat() != defaultStyle.getDataFormat())
					cstyle.setDataFormat(style.getDataFormat());
				if (style.getFillBackgroundColor() != defaultStyle.getFillBackgroundColor())
					cstyle.setFillBackgroundColor(style.getFillBackgroundColor());
				if (style.getFillForegroundColor() != defaultStyle.getFillForegroundColor())
					cstyle.setFillForegroundColor(style.getFillForegroundColor());
				if (style.getFillPattern() != defaultStyle.getFillPattern())
					cstyle.setFillPattern(style.getFillPattern());
				cell.setCellStyle(cstyle);
			}

		}
	}

	public void autoSizeColumn(int colIndex) {
		sheet.autoSizeColumn(colIndex);
	}

	public void autoSizeColumns(int numCols) {
		for (int i = 0; i < numCols; i++)
			sheet.autoSizeColumn(i);
	}
	
	public void autoSizeAllColumns() {
		if (sheet != null && sheet.getRow(0) != null) autoSizeColumns(sheet.getRow(0).getLastCellNum());
	}

	public XSSFCellStyle getWBStyle() {
		return workbook.createCellStyle();
	}

	public XSSFCreationHelper getHelper() {
		return workbook.getCreationHelper();
	}
}