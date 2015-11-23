package com.example.luopc.mycom;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {
    private int[] numberIds =new int[]{R.id.zero,R.id.one,R.id.two,R.id.three,R.id.four,
          R.id.five,R.id.six,R.id.seven,R.id.eight,R.id.nine,R.id.dot };             //存放数字按钮和小数点按钮

    private  int[] operationIds=new int[]{R.id.plus,R.id.mins,R.id.mul,R.id.div,R.id.mode,
          R.id.equal,R.id.clear,R.id.backspace};

    private Button[] numberBtns=new Button[numberIds.length];
    private Button[] operationBtns=new Button[operationIds.length];
     private EditText showInfo;
    private String str="0";
    private double num1;
    private double num2;
    private String  operationStr="";
    private  String result="";
    private boolean isFirstClicked=true;
    private boolean isLastNum=true;
    private boolean isOperationFirstClicked=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.gridlayout);
       NumberBtnListener numberListener=new NumberBtnListener();
        for(int i=0;i<numberBtns.length;i++){
            numberBtns[i]=(Button)findViewById(numberIds[i]);
            numberBtns[i].setOnClickListener(numberListener);

        }
        OperationBtnListener operationListener=new OperationBtnListener();
        for(int i=0;i<operationBtns.length;i++){
            operationBtns[i]=(Button)findViewById(operationIds[i]);
            operationBtns[i].setOnClickListener(operationListener);

        }
        showInfo=(EditText)findViewById(R.id.showInfo);
    }


        private class NumberBtnListener implements View.OnClickListener{
            public void onClick(View v){
                if(!isFirstClicked&&isLastNum){
                    if(v.getId()==R.id.dot){
                    if(str.indexOf(".")>=0){
                        return;
                    }
                    }
                    str+=((Button)v).getText().toString();
                    str=operationZero(str);

                }else{
                    str=((Button)v).getText().toString();
                    isFirstClicked=false;
                    isLastNum=true;
                    if(v.getId()==R.id.dot){
                        str="0";
                    }
                }showInfo.setText(str);
            }
        }

      private class OperationBtnListener implements View.OnClickListener{
          public void onClick(View v){
                if(isFirstClicked){       //第一次点击，并且是操作符
                    showInfo.setText(str);
                    return;
                }
              if(v.getId()==R.id.clear){
                  clear();
                  showInfo.setText(str);
                  return;
                }
              if(v.getId()==R.id.backspace){
                  str=str.substring(0,str.length()-1);

                  if(str.length()==0){
                      str="0";
                  }showInfo.setText(str);
                  return;
              }
              if(isLastNum){         //上一次点击是数字
                  if(isOperationFirstClicked){     //如果是第一次点击操作数
                    if(!"=".equals(((Button)v).getText().toString())){
                          num1=Double.parseDouble(str);
                        operationStr=((Button)v).getText().toString();
                        isOperationFirstClicked=false;
                        isLastNum=false;


                      }
                  }else{
                      num2=Double.parseDouble(str);
                 if("=".equals(((Button)v).getText().toString())){
                     double calResult=getReslt(operationStr);
                     showInfo.setText(result+"="+calResult+"");
                     clear();

                 }  else {
                     num1=getReslt(operationStr);
                     showInfo.setText(result+"="+num1+"");
                     operationStr=((Button)v).getText().toString();
                     isFirstClicked=false;
                     isLastNum=false;


                 }

                  }
              }else{
                  operationStr=((Button)v).getText().toString();

              }
          }
      }


    private  String operationZero(String str){
        if(str.indexOf(".")<0&&str.length()>1){
            while(str.startsWith("0")&&str.length()>1){
                str=str.substring(1);
            }
        }return str;
    }


    private double getReslt(String operationStr){
        if("-".equals(operationStr)){
            result =num1 +"-" + num2;
            return num1-num2;
        }else if ("+".equals(operationStr)){
            result=num1+"+"+num2;
            return num1+num2;
        }else if ("*".equals(operationStr)){
            result=num1+"*"+num2;
             return num1 * num2;
        }else  if("/".equals(operationStr)){
            result= num1+"/"+num2;
            return num1 / num2;
        }else  if("%".equals(operationStr)){
            result = num1+"%"+num2;
            return num1%num2;
        }
        return num2;
    }


    public  void  clear(){
            isFirstClicked=true;
        isOperationFirstClicked=true;
        num1=0;
        num2=0;
        operationStr="";
        str="0";

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}
