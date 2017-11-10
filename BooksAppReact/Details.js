import React, {Component} from 'react';
import {
    StyleSheet,
    Text,
    View,
    TextInput,
    ScrollView,
    Button
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


export default class Details extends Component{
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
        if(this.book.key!=0)
        {
            for(var i=0;i<books.length;i++)
            {
                if(books[i].key==this.book.key)
                {
                    books[i]=this.book;
                }
            }
        }
        this.props.navigation.navigate("BookList");
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
                        <TextInput style={styles.detailItem} onChangeText={(author)=>{this.book.author=author}}>{this.book.author}</TextInput>
                        <TextInput style={styles.detailItem} onChangeText={(genre)=>{this.book.genre=genre}}>{this.book.genre}</TextInput>
                        <TextInput style={styles.detailItem }onChangeText={(exchangeMethod)=>{this.book.exchangeMethod=exchangeMethod}}>{this.book.exchangeMethod}</TextInput>
                        <TextInput style={styles.detailItem} onChangeText={(location)=>{this.book.location=location}}>{this.book.location}</TextInput>

                    </View>
                </ScrollView>
                <Button title="Save Changes" onPress={() => this.updateDetails()}/>
            </View>
        );
    }
}