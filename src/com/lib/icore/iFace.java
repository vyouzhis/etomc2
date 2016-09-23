package com.lib.icore;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BaseiCore;
import org.ppl.common.ShowMessage;
import org.ppl.etc.UrlClassList;
import org.ppl.io.ProjectPath;
import org.ppl.plug.Img.ImgHandle;

public class iFace extends BaseiCore {
	private String className = null;
	private int W=64;
	private int H=64;
	public iFace() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub
		UrlClassList ucl = UrlClassList.getInstance();
		if (super.Init() == -1) {

			String err_url = ucl.BuildUrl("login", "");

			TipMessage(err_url, _CLang("error_passwd_or_name"));
		} else {

			List<String> rmc = porg.getRmc();
			if (rmc.get(0).equals("face")) {
				setUrl();
				getSlogo();
				super.View();
			} else if (rmc.get(0).equals("facenimg")) {
				isAutoHtml = false;
				super.setAjax(true);
				SaveNewImg();
			} else {
				isAutoHtml = false;
				super.setAjax(true);
				HandlerImg();
			}

		}
	}

	private void getSlogo() {
		String sql = "SELECT logo,slogo FROM `stock_user_info` where uid="
				+ igetUid() + "  limit 1 ";
		Map<String, Object> res = FetchOne(sql);
		if (res != null) {
			setRoot("slogo", "Data/" + res.get("slogo"));
			setRoot("rlogo", "Data/" + res.get("logo"));
		}
	}

	private void SaveNewImg() {

		Map<String, byte[]> file = porg.getUpload_string();
		String img = porg.getUpload_name().get("images");
		String[] imgExt = img.split("\\.");
		String ext = imgExt[imgExt.length - 1];

		ProjectPath pp = ProjectPath.getInstance();

		String name = "UserLogo/" + time() + "." + ext;

		pp.SaveFile(name, file.get("images"), false);

		saveSlogo(name);

		UrlClassList ucl = UrlClassList.getInstance();
		ShowMessage sm = ShowMessage.getInstance();
		sm.Redirect(ucl.Url("face"));
	}

	private void saveSlogo(String slogo) {
		String format = "UPDATE `stock_user_info` SET `slogo` = '%s' WHERE `stock_user_info`.`uid` = %d;";
		String sql = String.format(format, slogo, igetUid());

		try {
			update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void setUrl() {
		UrlClassList ucl = UrlClassList.getInstance();
		setRoot("icore", ucl.BuildUrl("icore", time() + ""));
		setRoot("balance", ucl.Url("balance"));
		setRoot("face", ucl.Url("face"));
		setRoot("facenimg", ucl.Url("facenimg"));
		setRoot("facelogo", ucl.Url("facelogo"));

		setRoot("istrategy_url", ucl.Url("strategy"));
		setRoot("itrade_url", ucl.Url("trade"));
		setRoot("irisk_url", ucl.Url("risk"));
		setRoot("iarbitrage_url", ucl.Url("arbitrage"));
	}

	private void HandlerImg() {
		UrlClassList ucl = UrlClassList.getInstance();
		ShowMessage sm = ShowMessage.getInstance();

		int x = (int) toFloat(porg.getKey("x"));
		int y = (int) toFloat(porg.getKey("y"));

		int width = (int) toFloat(porg.getKey("w"));
		if (width == 0) {
			sm.Redirect(ucl.Url("face"));
			return;
		}

		int height = (int) toFloat(porg.getKey("h"));
		// echo("x:"+x+" y:"+y+" w:"+width+" h:"+height);

		String sql = "SELECT slogo FROM `stock_user_info` where uid="
				+ igetUid() + "  limit 1 ";
		Map<String, Object> res = FetchOne(sql);
		String sourcePath = res.get("slogo").toString();

		String[] imgExt = sourcePath.split("\\.");
		String ext = imgExt[imgExt.length - 1];

		ProjectPath pp = ProjectPath.getInstance();
		sourcePath = pp.DataDir().getPath() + sourcePath;

		String tpath = "UserLogo/" + time() + "." + ext;
		String targetPath = pp.DataDir().getPath() + tpath;

		// echo(sourcePath+"=="+targetPath);
		try {
			ImgHandle.cutImage(sourcePath, targetPath, x, y, width, height);
			ImgHandle.zoom(targetPath, targetPath, W, H);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String format = "UPDATE `stock_user_info` SET `logo` = '%s' WHERE `stock_user_info`.`uid` = %d;";
		sql = String.format(format, tpath, igetUid());

		try {
			update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sm.Redirect(ucl.Url("face"));

	}

}
