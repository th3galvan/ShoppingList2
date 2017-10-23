package galvan.shoppinglist2;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {

    private ArrayList<ShoppingItem> itemList;                                                             //2
    private ShoppingListAdapter adapter;                                                           //1
    private ListView list;
    private Button btn_add;
    private EditText edit_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        list = (ListView) findViewById(R.id.list);
        btn_add = (Button) findViewById(R.id.btn_add);
        edit_item = (EditText) findViewById(R.id.edit_item);


        itemList = new ArrayList<>();                                                                   //2
        itemList.add(new ShoppingItem("Patatas",true));
        itemList.add(new ShoppingItem("Papel WC",true));
        itemList.add(new ShoppingItem("Zanahorias"));
        itemList.add(new ShoppingItem("Copas Danone"));

        adapter = new ShoppingListAdapter(this, android.R.layout.simple_list_item_1, itemList);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });

        edit_item.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                addItem();
                return true;
            }
        });

        list.setAdapter(adapter);                                                                       //1

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
            //ShoppingItem item = itemList.get(pos);
            //boolean checked = item.isChecked();
            //itemList.get(pos).setChecked(!checked);
            itemList.get(pos).toggleChecked();
                adapter.notifyDataSetChanged();
            }
        });


        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> list, View item, int pos, long id) {
                maybeRemoveItem(pos);
                return true;
            }
        });
    }

    private void maybeRemoveItem(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm);
        String fmt =getResources().getString(R.string.confirm_message);
        builder.setMessage(String.format(fmt, itemList.get(pos).getText()));

        builder.setPositiveButton(R.string.remove, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                itemList.remove(pos);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();

    }

    private void addItem() {
        String item_Text = edit_item.getText().toString();
        if(!item_Text.isEmpty())                                                                        //!item_text.equals("")
        {
            itemList.add(new ShoppingItem(item_Text));
            adapter.notifyDataSetChanged();                                                             //para refrescar los datos instantaneamente
            edit_item.setText("");
        }
        list.smoothScrollToPosition(itemList.size()-1);
    }
}