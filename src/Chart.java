
import java.util.ArrayList;
import java.util.List;
import java.awt.BasicStroke;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.SamplingXYLineRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class Chart extends ApplicationFrame {
	
	private static final long serialVersionUID = 1L;

	public Chart(String title) {
		super(title);
		XYDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 500));
		SamplingXYLineRenderer renderer = new SamplingXYLineRenderer();

		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesPaint(1, Color.ORANGE);
		renderer.setSeriesPaint(2, Color.YELLOW);
		 
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));
		renderer.setSeriesStroke(1, new BasicStroke(2.0f));
		renderer.setSeriesStroke(2, new BasicStroke(2.0f));
		 
		XYPlot plot = chart.getXYPlot();
		plot.setRenderer(renderer);
		
		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);
		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);
		
	    chart.getPlot().setBackgroundPaint(Color.WHITE);
		setContentPane(chartPanel);
		
		/*File imageFile = new File("Chart7.png");
		int width = 640;
		int height = 480;
		 
		try {
		    ChartUtilities.saveChartAsPNG(imageFile, chart, width, height);
		}
		catch (IOException e) {
		    System.err.println(e);
		}*/
	}

	private static XYDataset createDataset() {
		
		XYSeries series1 = new XYSeries("String comparison");
		XYSeries series2 = new XYSeries("Structure comparison");
		XYSeries series3 = new XYSeries("Min insertion order comparison");
		
		for (int i = 10; i < 500; i += 10) {
			List<Double> times1 = new ArrayList<>();
			List<Double> times2 = new ArrayList<>();
			List<Double> times3 = new ArrayList<>();
			for (int repetitions = 0; repetitions < 10; repetitions++) {
				//int[][] data = Main.generateRandomData(i, 100); // i trees of size 100
				int[][] data = Main.generateRandomData(100, i); // 100 trees of size i
				times1.add(Main.run("stringComparison", data));
				times2.add(Main.run("structureComparison", data));
				times3.add(Main.run("orderComparison", data));
			}
			series1.add(i, Main.averageTime(times1));
			series2.add(i, Main.averageTime(times2));
			series3.add(i, Main.averageTime(times3));
			
		}
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		dataset.addSeries(series3);
		return dataset;
	}

	private static JFreeChart createChart(XYDataset dataset) {
		
		
		JFreeChart chart = ChartFactory.createXYLineChart("Comparison of algorithms", "Tree size","Time (ms)", 
				dataset, PlotOrientation.VERTICAL, true, true, false);
		return chart;
	}
	
}