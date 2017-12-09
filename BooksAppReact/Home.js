import React, {Component} from 'react';
import {
    StyleSheet,
    Text,
    View,
    Button,
    AsyncStorage
} from 'react-native';

const styles= StyleSheet.create({
    header: {
        backgroundColor: 'white',
        alignItems: 'center',
        justifyContent: 'center',
        borderBottomWidth: 2,
        marginBottom: 3,
        borderBottomColor: '#71001a'
    },
    headerText: {
        color: '#71001a',
        fontSize: 30,
    },
})

export default  class Home extends Component{
    constructor(props) {
        super(props);

    }

    render(){
        const{navigate}=this.props.navigation;
        return (
            <View style={{marginTop:20}}>
                <View style={styles.header}>
                    <Text style={styles.headerText}>Book Sharing</Text>
                </View>
                <Button title="Book List" onPress={() => {navigate('BookList')}}/>
                <Button style={{paddingTop:20}} title="Tell a friend about this" onPress={() => {navigate('EmailPage')}}/>
            </View>
        );
    }
}