package top.someapp.dukelib.csv;

/**
 * csv文档的最小单位，单元格
 *
 * @author zwz
 * Created on 2020-11-19
 */
public class Cell {

    private int row;
    private int col;
    private String name;
    private BasicType type;
    private String rawValue;
}
