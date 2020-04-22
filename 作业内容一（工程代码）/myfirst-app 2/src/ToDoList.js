import React,{Component} from 'react';
import ListItem from './ListItem';
import NewItem from './NewItem';

class ToDoList extends Component{
    constructor(props){
        super(props);
        this.state = {
            todoList:[ {content:'(React practice list)',done:true} ]
            //content:'React practice',done:true
        }
    }
    componentDidMount(){
        //网络请求
       // var that = this;
        fetch('http:localhost:8080/api/tasks/',{
            method:"get"
        })
            .then(res => {
              return res.json();
           })
           .then(data => {
               console.log(data);
               this.setState({
                   todoList:data
               })
              
           })
    }

//    componentWillMount(){
//    fetch('/static/content.json')
//         .then(function(response){
//             return response.json();
//         })
//         .then(function(myTasks){
//             console.log(myTasks);
//             //this.setState({todoList:myTasks})
//         })
//     }
   onaddBtnClick(){
        fetch('http:localhost:8080/api/tasks/')
        .then(function(response){
            return response.json();
        })
        .then(function(myTasks){
            console.log(myTasks);
            //this.setState({todoList:myTasks})
        })
    //     fetch('/static/content.json').then(res => {
    //        return res.json()
    //    }).then(data => {
    //        const newdata = [...this.state.todoList,{content:data.content,done:false}];
    //        this.setState({todoList:newdata})
          
    //    this.setState({todoList:data});
    //        console.log(data);
    //    })
   }

    addNewItem =(newItemContent) => {
        const newList = [...this.state.todoList,{content:newItemContent,done:false}];
        this.setState({
            todoList:newList
           
        })

    }
 

    render(){
        var { allTasks } = this.state;
        return(
            <div>
                <div className="all">
                <h3>1.show all tasks</h3>
                <ul>
                    {
                        this.state.todoList.map((item,index)  => {
                            return <li key = {index}>{index,item.content}</li>
                        })
                    }
                    </ul>
                </div>
                <h3>2.Add and delete</h3>
 
                 <NewItem addItem ={this.addNewItem} />
                {
                this.state.todoList.map(item => <ListItem item={item}/>)
             }
             {/* {this.state.todoList.map(item =>{
                 return (<div>{item.content}</div>)
             })} */}
           
            </div>
        );
    }
    
}
export default ToDoList;