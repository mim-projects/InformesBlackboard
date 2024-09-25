package com.mimsoft.informesblackboard.application.modules.reports.dashboad_pdf.components.charts;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.mimsoft.informesblackboard.application.data.constants.Colors;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.AreaRendererEndType;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HistoryCharts {
    private final int width;
    private final int height;
    private JFreeChart chart;
    private CategoryDataset dataset;

    public HistoryCharts(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public HistoryCharts build(String x, String y) {
        chart = createChart(dataset, x, y);
        ChartPanel chartPanel = new ChartPanel(chart, false);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setMouseWheelEnabled(true);

        chartPanel.setMinimumDrawHeight(0);
        chartPanel.setMinimumDrawWidth(0);
        chartPanel.setMaximumDrawHeight(Integer.MAX_VALUE);
        chartPanel.setMaximumDrawWidth(Integer.MAX_VALUE);
        return this;
    }

    public HistoryCharts setDataset(String[] keyword, Double[] values) {
        double[][] vals = new double[1][values.length];
        for (int k = 0; k < values.length; k++) {
            vals[0][k] = values[k];
        }
        dataset = DatasetUtils.createCategoryDataset(new String[]{"Date"}, keyword, vals);
        return this;
    }

    public Image getImage() throws IOException, BadElementException {
        BufferedImage bufferedImage = chart.createBufferedImage(width, height);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        return Image.getInstance(byteArrayOutputStream.toByteArray());
    }

    private static JFreeChart createChart(CategoryDataset dataset, String x, String y) {
        JFreeChart chart = ChartFactory.createAreaChart("", x, y, dataset, PlotOrientation.VERTICAL, false, true, true);
        chart.getTitle().setVisible(false);
        if (chart.getLegend() != null) {
            chart.getLegend().setPosition(RectangleEdge.TOP);
            chart.getLegend().setFrame(BlockBorder.NONE);
            chart.getLegend().setLegendItemGraphicPadding(new RectangleInsets(0, 15, 0, 5));
            chart.setBackgroundPaint(Color.WHITE);
        }

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        Paint[] colors = new Paint[]{ ConvertColor(Colors.UABC_GREEN), ConvertColor(Colors.UABC_GREEN_1), ConvertColor(Colors.UABC_GREEN_2) };
        plot.setDrawingSupplier(new DefaultDrawingSupplier(colors, colors, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlinePaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.setForegroundAlpha(0.7f);

//        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        AreaRenderer renderer = (AreaRenderer) plot.getRenderer();
        renderer.setEndType(AreaRendererEndType.LEVEL);
        return chart;
    }

    private static Color ConvertColor(Colors colors) {
        String[] parts = colors.getRGB();
        return new Color(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }
}
