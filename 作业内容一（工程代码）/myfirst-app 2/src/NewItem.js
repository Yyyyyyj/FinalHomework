import React, { Component } from "react";
//import fetch from 'myFetch.';

class NewItem extends Component{
    constructor (props){
        super(props);
        this.state = {
            inputContent:'',
            status:''
        }
    }
    onInputChange = (event) => {
        this.setState({
            inputContent:event.target.value
        })

    }


 
    handleSubmit(event){
       event.preventDefault();
        console.log(this.state.inputContent)
        fetch("http://localhost:8080/api/tasks/",{
            method:"post",
            headers:{
                'Accept':'application/json,text/plain,*/*',
                'Content-Type':'application/x-www-form-urlencoded'
            },
            body:"content="+ this.state.inputContent
        })
        .then(res =>{
            return res.json()
        })
        .then(data =>{
            console.log(1000)
        })
        this.props.addItem(this.state.inputContent)
        this.setState({
            inputContent:''
        })
    }
        // that.props.addItem(that.state.inputContent);
        //  this.setState({
        //     inputContent:''
        // })

    // onaddBtnClick =() =>{
        
    //     this.props.addItem(this.state.inputContent)
    //     this.setState({
    //         inputContent:''
    //     })
    //  }
 //    AddTodo = ({dispatch}) => {
        // let input;
        // const clickAdd = async() =>{
        //     const params = {value: input.value};
        //     const data = await fetch('',{
        //         type:'GET',
        //         params,
        //     });
        //     console.log(data);
        // }
  //  }
    render(){
        return(
            <div>
                <form onSubmit ={this.handleSubmit.bind(this)}>
               <input value={this.state.inputContent} onChange={this.onInputChange}/>
               {/* <button onClick = {this.onaddBtnClick}>add</button> */}
               <input type ="submit" value ="Add" />
               </form>
              
            </div>
        
        )
    }
}
export default NewItem;