package Models;

public class Custom {

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_desc() {
        return _desc;
    }

    public void set_desc(String _desc) {
        this._desc = _desc;
    }

    private String _name;
    private String _desc;

    private String _link;
    private int _value1;

    public String getLink() {return _link;}
    public int getValue1() {return _value1;}

    public void set_link(String link){this._link = link;}
    public void set_value1(int value){this._value1 = value;}


    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    private Integer _id;

    public Custom()
    {
        _name = "";
        _desc = "";
    }

    public Custom(int _curid, String _curname)
    {
        _name = _curname;
        _desc = "";
        _id = _curid;
    }


    public Custom(int _curid, String _curname, String _curdesc)
    {
        _name = _curname;
        _desc = _curdesc;
        _id = _curid;
    }


    public Custom(int curid, String desc, String link, int value)
    {
        _desc = desc;
        _id = curid;
        _link = link;
        _value1 = value;
    }




}
