import React, {Component} from 'react';
import {
    StyleSheet,
    Text,
    View,
    ScrollView,
    Button,AsyncStorage,
    Picker
} from 'react-native';



const styles= StyleSheet.create({
    container: {
        flex: 1,
        // alignItems:'center',
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
        this.state={key:0,
            title:"",
            author:"",
            genre:"",
            exchangeMethod:"",
            location:"",
            rating:"1",
            votesNumber:"0"
        };
        if(this.props.navigation.state.params.book!==undefined)
        {
            var param=this.props.navigation.state.params.book;
            this.state.key=param.key;
            this.state.author=param.author;
            this.state.title=param.title;
            this.state.genre=param.genre;
            this.state.exchangeMethod=param.exchangeMethod;
            this.state.location=param.location;
            this.state.rating=param.rating;
                this.state.votesNumber=param.votesNumber;
        }
    }

    updateRating(newValue){
        var votes=parseInt(this.state.votesNumber)+1;
        var rating=parseInt(this.state.rating)+parseInt(newValue);

        var newbook={
            key:this.state.key,
            author:this.state.author,
            title:this.state.title,
            genre:this.state.genre,
            exchangeMethod:this.state.exchangeMethod,
            location:this.state.location,
            rating:rating,
            votesNumber:votes
        }
        //update chart


        AsyncStorage.mergeItem(String(newbook.key),JSON.stringify(newbook)).then(()=>
        this.props.navigation.state.params.persistBooks());
       // this.props.navigation.goBack();
    }

    render(){
        let data = [
            [0, 1],
            [1, 3],
            [3, 7],
            [4, 9],
        ];
        let r = parseInt(this.state.rating) * 100 / 5;
        let rr = (5 - parseInt(this.state.rating)) * 100 / 5;
        return (
            <View style={styles.container}>
                <ScrollView>
                    <View style={{
                        justifyContent:'center',
                       // alignItems:'center',
                    }}>

                        <Text style={styles.bookTitle}>{this.state.title}</Text>
                        <Text>Author:</Text>
                        <Text style={styles.detailItem}>{this.state.author}</Text>
                        <Text>Genre:</Text>
                        <Text style={styles.detailItem}>{this.state.genre}</Text>
                        <Text>Exchange Method:</Text>
                        <Text style={styles.detailItem }>{this.state.exchangeMethod}</Text>
                        <Text>Location:</Text>
                        <Text style={styles.detailItem}>{this.state.location}</Text>
                        <Text>Rate this book:</Text>
                        <Picker
                            selectedValue={this.state.rating}
                            onValueChange={(rating) =>{ this.setState({rating});this.updateRating(rating)}}>
                            <Picker.Item label="1" value="1" />
                            <Picker.Item label="2" value="2" />
                            <Picker.Item label="3" value="3" />
                            <Picker.Item label="4" value="4" />
                            <Picker.Item label="5" value="5" />
                        </Picker>


                    </View>
                </ScrollView>

                <Button title="Done" onPress={() => this.props.navigation.goBack()}/>
            </View>
        );
    }
}