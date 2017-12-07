import React, {Component} from 'react';
import {
    StyleSheet,
    Text,
    View,
    ScrollView,
    FlatList, Button, TextInput, ListView,
} from 'react-native';
import PopupDialog from "react-native-popup-dialog/src/PopupDialog"

const styles= StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        backgroundColor: '#fff',
        paddingTop: 30,
        justifyContent: 'center',
    },
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

    item: {
        fontSize: 15,
        justifyContent: 'center',
        marginBottom: 5,
    },
    linearView:{
        flexDirection:'row',}
})

export default class BookList extends Component{

    constructor(props) {
        super(props);
        this.ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
        this.state= {
            key:0,
            title:"",
            author:"",
            genre:"",
            exchangeMethod:"",
            location:"",
            dataSource:[],
            allbooks:[],
        };

       // this.book = {
       //     key: 0,
       //     title: "",
       //     author: "",
       //     genre: "",
       //     exchangeMethod: "",
        //    location: ""
       // };

        this.selectedItem= {
        key:0,
        title:"",
        author:"",
        genre:"",
        exchangeMethod:"",
        location:""
        };

        this.selectedId=0;
    }

    getId() {
        var id = 0;
        for(var i = 0; i < this.state.allbooks.length; i++) {
            if(this.state.allbooks[i].key > id) {
                id = this.state.allbooks[i].key;
            }
        }
        return id + 1;
    }

    AddBook(){
      var newBook={
          key:this.getId(),
          title:this.state.title,
          author:this.state.author,
          genre:this.state.genre,
          exchangeMethod:this.state.exchangeMethod,
          location:this.state.location
      };
      this.state.allbooks.push(newBook);
      this.setState({dataSource:this.ds.cloneWithRows(this.state.allbooks)});
    }

    DeleteSelectedBook(){
        console.log("Delete book:"+String(this.selectedItem.key)+";"+this.selectedItem.title)
        var bookToDelete=this.selectedItem;

        var index=this.state.allbooks.indexOf(bookToDelete);
        if(index>-1)
        {
            this.state.allbooks.splice(index,1);
            this.setState({dataSource:this.ds.cloneWithRows(this.state.allbooks)});
        }
    }

    render(){
        this.state.allbooks=books;
        this.state.dataSource = this.ds.cloneWithRows(this.state.allbooks);
        const{navigate}=this.props.navigation;
        return(
            <View style={styles.container}>
                <View style={styles.header}>
                    <Text style={styles.headerText}>Available Books</Text>
                </View>
                <View>
                <Button onPress={()=>this.popupDialogAdd.show()} title="Add book"/>
                </View>
                <ListView
                    dataSource={this.state.dataSource}
                    renderRow={(item,sectionID,rowID)=>
                            <View style={{flex:1,flexDirection:'row'}}>
                                <Text onPress={()=>{
                                this.selectedItem=item;
                                this.popupDialogMenu.show();
                                }
                                }>{item.title}</Text>
                            </View>}

                    />

                <PopupDialog ref={(popupDialog) => {
                    this.popupDialogAdd = popupDialog;
                }}>
                    <ScrollView>
                        <View style={{
                            justifyContent:'center',
                            alignItems:'center',
                        }}>
                            <Text>Title:</Text>
                            <TextInput style={styles.detailItem} onChangeText={(text) => this.state.title=text} />
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
                    <Button title="Add Book" onPress={() => {this.AddBook();this.popupDialogAdd.dismiss()}}/>

                </PopupDialog>


                <PopupDialog ref={(popupDialog) => {
                    this.popupDialogMenu = popupDialog;
                }} >

                    <View>
                        <Text>{this.selectedItem.title}</Text>
                        <Button title="View" onPress={() => {this.popupDialogMenu.dismiss()}}/>
                        <Button title="Edit" onPress={() => {this.popupDialogMenu.dismiss();navigate('Details',{book:this.selectedItem})}}/>
                        <Button title="Delete" onPress={() => {this.popupDialogMenu.dismiss();this.DeleteSelectedBook();}}/>
                    </View>
                </PopupDialog>
            </View>
        );
    }
}