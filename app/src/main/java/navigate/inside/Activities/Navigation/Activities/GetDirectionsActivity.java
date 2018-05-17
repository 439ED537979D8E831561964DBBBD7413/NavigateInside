package navigate.inside.Activities.Navigation.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.recognition.packets.Beacon;

import navigate.inside.Logic.BeaconListener;
import navigate.inside.Logic.MyApplication;
import navigate.inside.Logic.SysData;
import navigate.inside.Objects.BeaconID;
import navigate.inside.Objects.Node;
import navigate.inside.R;

public class GetDirectionsActivity extends AppCompatActivity implements BeaconListener {

    private TextView sNode,gNode;
    private CheckBox chElevator;
    private BeaconID CurrentBeacon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_directions);
        initView();
    }

    private void initView() {

        sNode = (TextView)findViewById(R.id.start);
        gNode = (TextView)findViewById(R.id.goal);
        chElevator = (CheckBox)findViewById(R.id.elevator);
    }

    public void BindText(Node n){
        //sNode.setText(n.getRooms().get(0).GetRoomName());
    }


    public void Search(View view) {
        // todo SEARCH DISABLED TEMPORARILY

        /*
        txtSNode = sNode.getText().toString();
        txtGNode = gNode.getText().toString();

        //temporary
        BeaconID FinishNode = new BeaconID(UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),886,13607);
        BeaconID StartNode = new BeaconID(UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),48613,34740);
       // StartNode = SysData.getInstance().getNodeIdByRoom(txtSNode);
        //FinishNode = SysData.getInstance().getNodeIdByRoom(txtGNode);

        if(StartNode == null){
            Toast.makeText(getContext(), "Start Poistion was not found !", Toast.LENGTH_SHORT).show();
        }
        if(FinishNode == null){
            Toast.makeText(getContext(), "Finish goal was not found !", Toast.LENGTH_SHORT).show();
        }

        PathFinder pf = PathFinder.getInstance();
        boolean b = chElevator.isChecked();

        // if b is true then ignore the stairs (don't expand stairs node) else go through stairs
        if(!pf.FindPath(StartNode, FinishNode, b).isEmpty()) {

            Intent intent = new Intent(getActivity(), PlaceViewActivity.class);
            intent.putExtra(Constants.INDEX, 0);
            startActivity(intent);
        }else
            Toast.makeText(getContext(), R.string.no_path_found, Toast.LENGTH_LONG).show();
            */
    }

    @Override
    protected void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);

        ((MyApplication)getApplication()).registerListener(this);
        ((MyApplication)getApplication()).startRanging();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // unregister beacon listeners
        ((MyApplication)getApplication()).stopRanging();
        ((MyApplication)getApplication()).unRegisterListener(this);
    }


    @Override
    public void onBeaconEvent(Beacon beacon) {
        BeaconID temp = new BeaconID(beacon.getProximityUUID(),beacon.getMajor(),beacon.getMinor());
        if(CurrentBeacon == null){
            CurrentBeacon = temp;
        }else{
            if(!CurrentBeacon.equals(temp)){
                CurrentBeacon = temp;
                if(SysData.getInstance().getNodeByBeaconID(CurrentBeacon) !=null){
                   //BindText(SysData.getInstance().getNodeByBeaconID(CurrentBeacon));
                }else{
                    Toast.makeText(this,"Failed to fetch location",Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

}