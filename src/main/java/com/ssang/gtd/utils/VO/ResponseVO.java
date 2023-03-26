package com.ssang.gtd.utils.VO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVO {
    private ResponseSystemVO _system_ = new ResponseSystemVO();
    private Object _data_ = new Object();

    /*public ResponseSystemVO get_stystem_(){
        return _system_;
    }
    public void set_stystem_(ResponseSystemVO _system_){
        this._system_ = _system_;
    }
    public Object get_data_(){
        return _data_;
    }
    public void set_stystem_(Object _data_){
        this._data_=_data_;
    }*/
}
