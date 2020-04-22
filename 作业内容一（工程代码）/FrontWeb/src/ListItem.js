import React, { Component } from 'react';
import './TodoList.css';


class ListItem extends Component {

    constructor(props) {
        super(props)
        this.state = {
            textStyle: "item",
            buttonStyle: "btn",
            buttonStr: "完成",
            todoId: this.props.id,
            todoContent: this.props.content
        }
    }


    handleSubmit(event) {
        event.preventDefault();
        var newContent = prompt("请输入新的任务内容", this.state.todoContent);
        console.log(newContent)
        if (newContent.trim() === '') {
            window.alert("输入内容不能为空")
        } else {
            var url = '/api/tasks/';
            var data = {
                id: this.state.todoId,
                content: newContent
            };
            fetch(url, {
                method: "PUT",
                body: JSON.stringify(data),
                headers: new Headers({
                    'Content-Type': 'application/json'
                })
            })
                .then(res => {
                    return res.json()
                })
                .then(data => {
                    if (data.status === false) {
                        window.alert(data.message)
                    } else {
                        this.setState({
                            todoContent: newContent
                        })
                    }
                })
        }

    }


    handleSubmit1(event) {
        event.preventDefault();
        var url = "/api/tasks/" + this.state.todoId
        console.log(url)
        fetch(url, {
            method: "DELETE",
        })
            .then(res => {
                return res.json()
            })
            .then(data => {
                if (data.status === false) {
                    window.alert(data.message)
                }else{
                    window.location.reload()
                }
            })
    }

    render() {


        return (
            <tr >
                <th>
                    <p className={this.state.textStyle}>{this.state.todoContent}</p>
                </th>
                <th>
                    <button className={this.state.buttonStyle} onClick={this.handleSubmit1.bind(this)} >完成</button>
                </th>
                <th>
                    <button className={this.state.buttonStyle} onClick={this.handleSubmit.bind(this)} >修改</button>
                </th>
            </tr>
        );
    }
}

export default ListItem;