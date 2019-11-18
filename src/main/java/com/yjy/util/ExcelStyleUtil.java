package com.yjy.util;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.entity.params.ExcelForEachParams;
import cn.afterturn.easypoi.excel.export.styler.IExcelExportStyler;
import org.apache.poi.ss.usermodel.*;

/**
 * ��дIExcelExportStyler
 * @author xiongbin
 * @since 2018-11-20
 */
public class ExcelStyleUtil implements IExcelExportStyler {
	private static final short STRING_FORMAT = (short) BuiltinFormats.getBuiltinFormat("TEXT");
	private static final short FONT_SIZE_TEN = 10;
	private static final short FONT_SIZE_ELEVEN = 11;
	private static final short FONT_SIZE_TWELVE = 12;
	/**
	 * �������ʽ
	 */
	private CellStyle headerStyle;
	/**
	 * ÿ�б�����ʽ
	 */
	private CellStyle titleStyle;
	/**
	 * ��������ʽ
	 */
	private CellStyle styles;

	public ExcelStyleUtil(Workbook workbook) {
		this.init(workbook);
	}

	/**
	 * ��ʼ����ʽ
	 *
	 * @param workbook
	 */
	private void init(Workbook workbook) {
		this.headerStyle = initHeaderStyle(workbook);
		this.titleStyle = initTitleStyle(workbook);
		this.styles = initStyles(workbook);
	}

	/**
	 * �������ʽ
	 *
	 * @param color
	 * @return
	 */
	@Override
	public CellStyle getHeaderStyle(short color) {
		return headerStyle;
	}

	/**
	 * ÿ�б�����ʽ
	 *
	 * @param color
	 * @return
	 */
	@Override
	public CellStyle getTitleStyle(short color) {
		return titleStyle;
	}

	/**
	 * ��������ʽ
	 *
	 * @param parity ����������ʾ��ż��
	 * @param entity ��������
	 * @return ��ʽ
	 */
	@Override
	public CellStyle getStyles(boolean parity, ExcelExportEntity entity) {
		return styles;
	}

	/**
	 * ��ȡ��ʽ����
	 *
	 * @param dataRow ������
	 * @param obj     ����
	 * @param data    ����
	 */
	@Override
	public CellStyle getStyles(Cell cell, int dataRow, ExcelExportEntity entity, Object obj, Object data) {
		return getStyles(true, entity);
	}

	/**
	 * ģ��ʹ�õ���ʽ����
	 */
	@Override
	public CellStyle getTemplateStyles(boolean isSingle, ExcelForEachParams excelForEachParams) {
		return null;
	}

	/**
	 * ��ʼ��--�������ʽ
	 *
	 * @param workbook
	 * @return
	 */
	private CellStyle initHeaderStyle(Workbook workbook) {
		CellStyle style = getBaseCellStyle(workbook);
		style.setFont(getFont(workbook, FONT_SIZE_TWELVE, true));
		return style;
	}

	/**
	 * ��ʼ��--ÿ�б�����ʽ
	 *
	 * @param workbook
	 * @return
	 */
	private CellStyle initTitleStyle(Workbook workbook) {
		CellStyle style = getBaseCellStyle(workbook);
		style.setFont(getFont(workbook, FONT_SIZE_ELEVEN, true));
		//����ɫ
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
	}

	/**
	 * ��ʼ��--��������ʽ
	 *
	 * @param workbook
	 * @return
	 */
	private CellStyle initStyles(Workbook workbook) {
		CellStyle style = getBaseCellStyle(workbook);
		style.setFont(getFont(workbook, FONT_SIZE_TEN, false));
		style.setDataFormat(STRING_FORMAT);
		return style;
	}

	/**
	 * ������ʽ
	 *
	 * @return
	 */
	private CellStyle getBaseCellStyle(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		//�±߿�
		style.setBorderBottom(BorderStyle.THIN);
		//��߿�
		style.setBorderLeft(BorderStyle.THIN);
		//�ϱ߿�
		style.setBorderTop(BorderStyle.THIN);
		//�ұ߿�
		style.setBorderRight(BorderStyle.THIN);
		//ˮƽ����
		style.setAlignment(HorizontalAlignment.CENTER);
		//���¾���
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		//�����Զ�����
		style.setWrapText(true);
		return style;
	}

	/**
	 * ������ʽ
	 *
	 * @param size   �����С
	 * @param isBold �Ƿ�Ӵ�
	 * @return
	 */
	private Font getFont(Workbook workbook, short size, boolean isBold) {
		Font font = workbook.createFont();
		//������ʽ
		font.setFontName("����");
		//�Ƿ�Ӵ�
		font.setBold(isBold);
		//�����С
		font.setFontHeightInPoints(size);
		return font;
	}
}
