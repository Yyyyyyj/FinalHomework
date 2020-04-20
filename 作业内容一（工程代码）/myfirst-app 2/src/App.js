import React, { useCallback ,Component} from 'react';
import logo from './logo.svg';
import './App.css';
import ToDoListHeader from './ToDoListHeader';
import ToDoList from './ToDoList';

class App extends Component{

  render(){

    const todoList = [                                                                                                                                                                                                                                                                                                             
      'React practice'
     ];
 
    return(
      <div className="App">
      <ToDoListHeader />
      <ToDoList />
      </div>
    );
    const changeItem = document.getElementsByTagName("p");
    if (changeItem.done == false){
      changeItem.style.backgroundColor = 'red';
    }
   
  }
}


// function App() {
//   const todoList = [
//     'React practice',
//     'game time',
//   ];


//   return (
//     <div className="App">
//       <h2>
//         Todo List
//       </h2>
//       {
//         todoList.map(item => (<p>{item}</p>))
//       }
//       </div>




//   );
// }

export default App;
