package com.shaman.labka;

public class ColorWorker {

    private Dictionary<Integer, Integer> _Colors;
    private Tuple<Integer, Integer> _currentColor;

    public ColorWorker(){
        _Colors = new Dictionary();
        AddColors();

        _currentColor= new Tuple<>(_Colors.getKeyAt(0), _Colors.getValueByKey(_Colors.getKeyAt(0)));

    }

    public Dictionary<Integer, Integer> GetColors(){
        return _Colors;
    }
    public Tuple<Integer, Integer> GetCurrentColor(){
        return _currentColor;
    }
    private void AddColors() {
        _Colors.put(R.color.black, R.string.color_Black);
        _Colors.put(R.color.violet, R.string.color_Violet);
        _Colors.put(R.color.blue, R.string.color_Blue);
        _Colors.put(R.color.green, R.string.color_Green);
        _Colors.put(R.color.yellow, R.string.color_Yellow);
        _Colors.put(R.color.orange, R.string.color_Orange);
        _Colors.put(R.color.red, R.string.color_Red);
    }

    public Integer getValueByKey(Integer colorID) {
        return _Colors.getValueByKey(colorID);
    }
    public Integer getKeyAt(Integer position) {
        return _Colors.getKeyAt(position);
    }

    public int size() {
        return _Colors.size();
    }
}
