import React, {Component} from 'react';
import {
    Text,
    View,
    TextInput,
    Button,
    Linking
} from 'react-native';

export default class EmailPage extends Component{
    constructor(props) {
        super(props);
        this.state = {email: "i.diana_cera@yahoo.com", subject: "Hello from BookSharing App"};
    }
    render () {
        return (
            <View style={{marginTop:20}}>
                <Text>E-mail:</Text>
                <TextInput style={{width:350,height:40,borderColor:'gray'}}
                           onChangeText={(email)=>this.setState({email})}/>
                <Text>Subject:</Text>
                <TextInput style={{width:350,height:40,borderColor:'gray'}}
                           onChangeText={(subject)=>this.setState({subject})}/>

                <Text>Message:</Text>
                <TextInput style={{height:100,width:350,marginTop:10,borderColor:'gray',textAlign:'left'}} multiline={true} onChangeText=
                    {(message)=>this.setState({message})}
                />

                <Button title="Send" onPress={()=>{

                    if(this.state.email!="")
                    {
                        receiver=this.state.email;
                        subject=this.state.subject;
                        body=this.state.message;
                        all="mailto:"+receiver+"?subject="+subject+"&body="+body;
                        Linking.openURL(all)}}
                }>

                </Button>
            </View>
        );
    }
}