import React, {Component} from 'react';
import {
    StyleSheet,
    Text,
    View,
    TextInput,
    ScrollView,
    Button,
    AsyncStorage
} from 'react-native';


const styles= StyleSheet.create({
    container: {
        flex: 1,
        alignItems:'center',
        backgroundColor: '#fff',
        paddingTop:30,
        justifyContent: 'center',
    },

    bookTitle:{
        textAlign:'center',
        color:'#71001a' ,
        fontSize:30,
    },
    detailItem:{
        width:150,
        textAlign:'center',
        marginBottom:10,
        fontSize:15,
    },
})


export default class AddBook extends Component{
    constructor(props)
    {
        super(props);
        this.state={key:0,
            title:"",
            author:"",
            genre:"",
            exchangeMethod:"",
            location:""
        };
    }

    getId() {
        allbooks=this.props.navigation.state.params.allbooks;
        var id = 0;
        for(var i = 0; i < allbooks.length; i++) {
            if(allbooks[i].key > id) {
                id = allbooks[i].key;
            }
        }
        return id + 1;
    }

    addBook(){
        var id=this.getId();

        AsyncStorage.setItem(String(id),JSON.stringify({
            key:id,
            title:this.state.title,
            author:this.state.author,
            genre:this.state.genre,
            exchangeMethod:this.state.exchangeMethod,
            location:this.state.location
        }));
        this.props.navigation.state.params.persistBooks();
        this.props.navigation.goBack();
    }

    render(){

        return (
            <View style={styles.container}>
                <ScrollView>
                    <View style={{
                        justifyContent:'center',
                        alignItems:'center',
                    }}>
                        <Text>Title:</Text>
                        <TextInput  style={styles.detailItem} onChangeText={(text) => this.state.title=text}  />
                        <Text>Author:</Text>
                        <TextInput style={styles.detailItem} onChangeText={(author)=>{this.state.author=author}}/>
                        <Text>Genre:</Text>
                        <TextInput style={styles.detailItem} onChangeText={(genre)=>{this.state.genre=genre}}/>
                        <Text>Exchange Method:</Text>
                        <TextInput style={styles.detailItem }onChangeText={(exchangeMethod)=>{this.state.exchangeMethod=exchangeMethod}}/>
                        <Text>Location</Text>
                        <TextInput style={styles.detailItem} onChangeText={(location)=>{this.state.location=location}}/>

                    </View>
                </ScrollView>
                <Button title="Add Book" onPress={() => this.addBook()}/>
            </View>
        );
    }
}