import React, { Component } from 'react';
import './TodoList.css';
import NewItem from './NewItem';
import ListItem from './ListItem';

class TodoList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            todoList: [],
        }
    }

    componentWillMount() {
        var url = "/api/tasks/"
        fetch(url, {
            method: "GET",
        })
            .then(response => {
                response.json()
                    .then(data => {
                        const temp = data.data
                        console.log(temp)
                        this.setState({ todoList: temp })
                    })
            })
    }


    render() {
        return (
            <div>
                <table className="table">
                <tbody>
                        {
                            (this.state.todoList|| []).map(todo => <ListItem id={todo.id} content={todo.content} />)
                        }
                    <NewItem addNewItem={this.addNewItem} />
                </tbody>
                </table>
            </div>
        );
    }
}

export default TodoList;