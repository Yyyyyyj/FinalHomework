import React, { Component } from 'react';
import './TodoList.css';

class NewItem extends Component {

    constructor(props) {
        super(props);
        this.state = {
            inputContent: ''
        }
    }

    onInputChange = (event) => {
        this.setState(
            { inputContent: event.target.value }
        )
    }
    onAddBtnClick = () => {
        if (this.state.inputContent.trim() === '') {
            window.alert("请填入list内容");
        } else {
            var url = '/api/tasks/';
            var data = { content: this.state.inputContent };
            console.log(data)
            fetch(url, {
                method: 'POST',
                body: JSON.stringify(data),
                headers: new Headers({
                    'Content-Type': 'application/json'
                })
            }).then(res => res.json())
                .catch(error => console.error('Error:', error))
                .then(data => {
                    console.log(data)
                    if (data.status === false) {
                        window.alert(data.message);
                    } else {
                        window.location.reload();
                    }
                    this.setState({
                        inputContent: ''
                    })
                });
        }
    }

    render() {
        return (
            <tr>
                <th>
                    <input type="text" value={this.state.inputContent} onChange={this.onInputChange} />
                </th>
                <th>
                    <button className="btn" onClick={this.onAddBtnClick}>添加list</button>
                </th>
            </tr>
        );
    }
}

export default NewItem;