package rocoo.cameraapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity implements View.OnClickListener {

    private Button generalButton, watermarkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        generalButton = (Button) findViewById(R.id.general);
        watermarkButton = (Button) findViewById(R.id.watermark);
        generalButton.setOnClickListener(this);
        watermarkButton.setOnClickListener(this);
    }

    /**
     * MARK - View.OnClickListener
     **/
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.general:
                intent = new Intent(this, GeneralCamera.class);
                startActivity(intent);
                break;
            case R.id.watermark:
                intent = new Intent(this, WatermarkCamera.class);
                startActivity(intent);
                break;
        }
    }

}
