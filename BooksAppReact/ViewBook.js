import React, {Component} from 'react';
import {
    StyleSheet,
    Text,
    View,
    ScrollView,
    Button,AsyncStorage
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


export default class ViewBook extends Component{
    constructor(props)
    {
        super(props);
        this.book={key:0,
            title:"",
            author:"",
            genre:"",
            exchangeMethod:"",
            location:""
        };
        if(this.props.navigation.state.params.book!==undefined)
        {
            var param=this.props.navigation.state.params.book;
            this.book.key=param.key;
            this.book.author=param.author;
            this.book.title=param.title;
            this.book.genre=param.genre;
            this.book.exchangeMethod=param.exchangeMethod;
            this.book.location=param.location;
        }
    }

    updateDetails(){
        //AsyncStorage.mergeItem(String(this.book.key),JSON.stringify(this.book));
       // this.props.navigation.state.params.persistBooks();
       // this.props.navigation.goBack();
    }

    render(){

        return (
            <View style={styles.container}>
                <ScrollView>
                    <View style={{
                        justifyContent:'center',
                        alignItems:'center',
                    }}>

                        <Text style={styles.bookTitle}>{this.book.title}</Text>
                        <Text>Author:</Text>
                        <Text style={styles.detailItem}>{this.book.author}</Text>
                        <Text>Genre:</Text>
                        <Text style={styles.detailItem}>{this.book.genre}</Text>
                        <Text>Exchange Method:</Text>
                        <Text style={styles.detailItem }>{this.book.exchangeMethod}</Text>
                        <Text>Location:</Text>
                        <Text style={styles.detailItem}>{this.book.location}</Text>

                    </View>
                </ScrollView>
                <Button title="Done" onPress={() => this.props.navigation.goBack()}/>
            </View>
        );
    }
}