import React , {Component} from 'react';
import './ListItem.css';
class ListItem extends Component{
    constructor(props){
        super(props);
        this.onEdit2 = this.onEdit2.bind(this);
        this.state = {
            done:false,
            input_content:this.props.item.content
        }                                                                                                         

    }


onAddBtnClick = () =>{
       
    //   //  this.state.style.color= 'gray';
    //   var fontColor = document.getElementsByClassName("item");
    //   fontColor.color = 'gray';

     this.setState({
         done:true,
         
    })
      
   }

onEdit2(e){
    let value = e.target.value;
    this.setState({
        input_content:value
    })
    this.props.item.content = value;
}
onEditBtnClick = () =>{
    


    // this.setState({
    //     input_content:''
    // })

   }


// onDelBtnClick = () =>{
//     let modul = document.getElementsByTagName('div');

    

// }
onDelBtnClick(event){
    // event.preventDefault();
    var odiv = document.getElementById('div1');
    var obt = document.getElementById("bt");
    odiv.parentNode.removeChild(odiv)
    // fetch("http://localhost:8080/api/tasks/",{
    //     method:"post",
    //     headers:{
    //         'Accept':'application/json,text/plain,*/*',
    //         'Content-Type':'application/x-www-form-urlencoded'
    //     },
    //     body:"content="+ this.state.input_content
    // })
    // .then(res =>{
    //     return res.json()
    // })
    // .then(data =>{
        // if(data)
        // {
        //     var odiv = document.getElementById('div1');
        //     var obt = document.getElementById("bt");
        //     odiv.parentNode.removeChild(odiv) 
        // }
    // })
}

  
// const ListItem = (props) =>{ 
    render(){
     if(this.props.item.done){
         return  <p className="done-item">{this.props.item.content}</p>
       //  style={{color:this.state.style.color}}  
               
     }else{
         return <div id = "div1"><input   className="item" style ={{color:this.state.color,border:0}} onChange ={this.onEdit2} value = {this.state.input_content}/>
         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

        
          {/* <button style ={{border:0,color: 'red',backgroundColor:'white',fontFamily:'Monospace',fontSize:20}} onClick = {this.onEditBtnClick}>delete</button> */}
      <input type="button" id="bt" value="delete" onClick={this.onDelBtnClick} />
         
         </div>
    }
    

    }

}
    




export default ListItem;