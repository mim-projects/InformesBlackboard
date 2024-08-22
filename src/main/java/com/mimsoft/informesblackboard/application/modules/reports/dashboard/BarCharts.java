package com.mimsoft.informesblackboard.application.modules.reports.dashboard;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.mimsoft.informesblackboard.application.data.constants.Colors;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

class BarCharts {
    private final int width;
    private final int height;
    private final JFreeChart chart;

    public BarCharts(int width, int height) {
        this.width = width;
        this.height = height;
        chart = createChart(createDataset());
        ChartPanel chartPanel = new ChartPanel(chart, false);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setMouseWheelEnabled(true);

        chartPanel.setMinimumDrawHeight(0);
        chartPanel.setMinimumDrawWidth(0);
        chartPanel.setMaximumDrawHeight(Integer.MAX_VALUE);
        chartPanel.setMaximumDrawWidth(Integer.MAX_VALUE);
    }

    public Image getImage() throws IOException, BadElementException {
        BufferedImage bufferedImage = chart.createBufferedImage(width, height);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        return Image.getInstance(byteArrayOutputStream.toByteArray());
    }

    private static CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(7445, "Tijuana", "Docentes");
        dataset.addValue(24448, "Mexicali", "Docentes");
        dataset.addValue(4297, "Ensenada", "Docentes");
        dataset.addValue(7445, "Tijuana", "Estudiantes");
        dataset.addValue(24448, "Mexicali", "Estudiantes");
        dataset.addValue(4297, "Ensenada", "Estudiantes");
        return dataset;
    }

    private static JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart("", null, null, dataset);
        chart.getTitle().setVisible(false);
        chart.getLegend().setPosition(RectangleEdge.TOP);
        chart.getLegend().setFrame(BlockBorder.NONE);
        chart.getLegend().setLegendItemGraphicPadding(new RectangleInsets(0, 15, 0, 5));
        chart.setBackgroundPaint(Color.WHITE);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        Paint[] colors = new Paint[]{ ConvertColor(Colors.UABC_YELLOW), ConvertColor(Colors.UABC_GREEN), ConvertColor(Colors.UABC_BLUE) };
        plot.setDrawingSupplier(new DefaultDrawingSupplier(colors, colors, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlinePaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.BLACK);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setBarPainter(new StandardBarPainter());
        return chart;
    }

    private static Color ConvertColor(Colors colors) {
        String[] parts = colors.getRGB();
        return new Color(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }
}
