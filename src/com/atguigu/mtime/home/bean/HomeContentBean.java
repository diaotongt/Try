package com.atguigu.mtime.home.bean;

import java.util.List;

/**
 * 首页内容部分Bean
 * @author Wangnan
 *
 */
public class HomeContentBean {

	public List<AdvItem> advList;
	public AreaFirst areaFirst;
	public AreaSecond areaSecond;
	public HotMovie hotMovie;
	public HotPerson hotPerson;
	public List<HotPoint> hotPoints;
	public List<MallEntry> mallEntrys;
	public List<TopPoster> topPosters;
	
	
	public class AdvItem{
		public GotoPage gotoPage;
		public String img;
		public String img2;
		public String url;
	}

	public class GotoPage{
		public String gotoType;
		public Parameters parameters;
		public String relatedTypeUrl;
		public String url;
	}
	
	public class Parameters{
		public int goodsId;
	}
	
	public class AreaFirst{
		
	}
	
	public class AreaSecond{
		public SubFifth subFifth;
		public SubFirst subFirst;
		public SubFourth subFourth;
		public SubSecond subSecond;
		public SubThird subThird;
	}
	
	public class SubFifth{
		public int goodsId;
		public GotoPage gotoPage;
		public int id;
		public String image;
		public String image2;
		public String title;
		public String titleColor;
		public String titleSmall;
	}
	
	public class SubFirst{
		public int goodsId;
		public GotoPage gotoPage;
		public int id;
		public String image;
		public String image2;
		public String title;
		public String titleColor;
		public String titleSmall;
	}
	
	public class SubFourth{
		public int goodsId;
		public GotoPage gotoPage;	
		public String image;
		public String image2;
	}
	
	public class SubSecond{
		public int goodsId;
		public GotoPage gotoPage;
		public int id;
		public String image;
		public String image2;
		public String title;
		public String titleColor;
		public String titleSmall;		
	}
	
	public class SubThird{
		public int goodsId;
		public GotoPage gotoPage;
		public int id;
		public String image;
		public String image2;
		public String title;
		public String titleColor;
		public String titleSmall;		
	}
	
	public class HotMovie{
		public Movie movie;
		public int newsId;
		public String title;
		public String topCover;
	}
	
	public class Movie{
		public String desc;
		public String image;
		public int movieId;
		public String titleCn;
		public String titleEn;
		public String year;
	}
	
	public class HotPerson{
		
	}
	
	public class HotPoint{
		public int commentCount;
		public String desc;
		public int id;
		public List<Image> images;
		public String img;
		public String img2;
		public String img3;
		public long publishTime;
		public String tag;
		public String title;
		public int type;
	}
	
	public class Image{
		public String desc;
		public int gId;
		public String title;
		public String url1;
		public String url2;
	}
	
	public class MallEntry{
		
	}
	
	public class TopPoster{
		
	}
}
