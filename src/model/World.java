package model;

import java.awt.Color;
import java.io.Serializable;

import shared.Point;

/**
 * Contains both stateless and stateful data about a game world, including all
 * its territories and their current state.
 * 
 * Since this class is stateful, it should not be recycled throughout matches.
 */
class World implements Serializable {

	private static final long serialVersionUID = -1108366210688196375L;
	
	private String mapForegroundPath;
	public String getMapForegroundPath() {
		return mapForegroundPath;
	}
	
	private String mapBackgroundPath;
	public String getMapBackgroundPath() {
		return mapBackgroundPath;
	}
	
	private Continent[] continents;
	public Continent[] getContinents() {
		return continents;
	}
	
	private World() {
	}
	
	public static World generateDefaultWorld() {
		World world = new World();
		
		world.mapForegroundPath = "/images/war_tabuleiro_mapa copy.png";
		world.mapBackgroundPath = "/images/war_tabuleiro_fundo.png"; 
		
		world.continents = new Continent[6];
		
		Territory alaska = new Territory("Alasca", new Point(82f/1024, 130f/768), new Point[] { new Point(0.083008f, 0.154948f), new Point(0.070312f, 0.178385f), new Point(0.056641f, 0.210938f), new Point(0.108398f, 0.210938f), new Point(0.129883f, 0.154948f) });
		Territory calgary = new Territory("Calgary", new Point(174f/1024, 142f/768), new Point[] { new Point(0.130859f, 0.154948f), new Point(0.123047f, 0.175781f), new Point(0.142578f, 0.223958f), new Point(0.217773f, 0.223958f), new Point(0.225586f, 0.205729f), new Point(0.239258f, 0.205729f), new Point(0.251953f, 0.174479f), new Point(0.276367f, 0.174479f), new Point(0.264648f, 0.145833f), new Point(0.235352f, 0.145833f), new Point(0.224609f, 0.171875f), new Point(0.207031f, 0.171875f), new Point(0.203125f, 0.162760f), new Point(0.147461f, 0.161458f), new Point(0.145508f, 0.154948f) });
		Territory greenland = new Territory("Groenlandia", new Point(305f/1024, 108f/768), new Point[] { new Point(0.264648f, 0.145833f), new Point(0.278320f, 0.117188f), new Point(0.362305f, 0.117188f), new Point(0.370117f, 0.131510f), new Point(0.370117f, 0.136719f), new Point(0.362305f, 0.151042f), new Point(0.356445f, 0.152344f), new Point(0.342773f, 0.187500f), new Point(0.326172f, 0.187500f), new Point(0.320312f, 0.174479f), new Point(0.276367f, 0.173177f) });
		Territory vancouver = new Territory("Vancouver", new Point(144f/1024, 188f/768), new Point[] { new Point(0.122070f, 0.175781f), new Point(0.108398f, 0.210938f), new Point(0.116211f, 0.230469f), new Point(0.098633f, 0.269531f), new Point(0.104492f, 0.279948f), new Point(0.208008f, 0.279948f), new Point(0.225586f, 0.242188f), new Point(0.217773f, 0.223958f), new Point(0.142578f, 0.223958f) });
		Territory quebec = new Territory("Quebec", new Point(270f/1024, 196f/768), new Point[] { new Point(0.208984f, 0.278646f), new Point(0.289062f, 0.279948f), new Point(0.295898f, 0.263021f), new Point(0.307617f, 0.263021f), new Point(0.304688f, 0.273438f), new Point(0.317383f, 0.272135f), new Point(0.321289f, 0.260417f), new Point(0.314453f, 0.242188f), new Point(0.323242f, 0.223958f), new Point(0.328125f, 0.233073f), new Point(0.334961f, 0.217448f), new Point(0.332031f, 0.205729f), new Point(0.312500f, 0.205729f), new Point(0.310547f, 0.201823f), new Point(0.281250f, 0.201823f), new Point(0.275391f, 0.217448f), new Point(0.265625f, 0.217448f), new Point(0.255859f, 0.240885f), new Point(0.225586f, 0.242188f) });
		Territory california = new Territory("California", new Point(110f/1024, 265f/768), new Point[] { new Point(0.103516f, 0.279948f), new Point(0.082031f, 0.329427f), new Point(0.086914f, 0.342448f), new Point(0.075195f, 0.369792f), new Point(0.087891f, 0.397135f), new Point(0.123047f, 0.395833f), new Point(0.172852f, 0.279948f) });
		Territory texas = new Territory("Texas", new Point(164f/1024, 264f/768), new Point[] { new Point(0.123047f, 0.397135f), new Point(0.141602f, 0.438802f), new Point(0.196289f, 0.316406f), new Point(0.225586f, 0.315104f), new Point(0.239258f, 0.279948f), new Point(0.173828f, 0.278646f) });
		Territory newYork = new Territory("Nova Iorque", new Point(203f/1024, 266f/768), new Point[] { new Point(0.162109f, 0.397135f), new Point(0.176758f, 0.397135f), new Point(0.199219f, 0.447917f), new Point(0.206055f, 0.432292f), new Point(0.195312f, 0.408854f), new Point(0.202148f, 0.391927f), new Point(0.209961f, 0.391927f), new Point(0.224609f, 0.356771f), new Point(0.231445f, 0.356771f), new Point(0.237305f, 0.338542f), new Point(0.254883f, 0.338542f), new Point(0.271484f, 0.295573f), new Point(0.283203f, 0.294271f), new Point(0.289062f, 0.279948f), new Point(0.239258f, 0.279948f), new Point(0.225586f, 0.316406f), new Point(0.196289f, 0.316406f) });
		Territory mexico = new Territory("Mexico", new Point(138f/1024, 354f/768), new Point[] { new Point(0.100586f, 0.459635f), new Point(0.107422f, 0.444010f), new Point(0.099609f, 0.423177f), new Point(0.102539f, 0.416667f), new Point(0.132812f, 0.484375f), new Point(0.141602f, 0.484375f), new Point(0.157227f, 0.524740f), new Point(0.166992f, 0.524740f), new Point(0.174805f, 0.540365f), new Point(0.183594f, 0.520833f), new Point(0.178711f, 0.511719f), new Point(0.173828f, 0.510417f), new Point(0.166992f, 0.498698f), new Point(0.170898f, 0.493490f), new Point(0.162109f, 0.475260f), new Point(0.166992f, 0.462240f), new Point(0.160156f, 0.446615f), new Point(0.157227f, 0.454427f), new Point(0.147461f, 0.455729f), new Point(0.123047f, 0.397135f), new Point(0.087891f, 0.397135f), new Point(0.080078f, 0.414062f) });
		Territory spain = new Territory("Espanha", new Point(447f/1024, 283f/768), new Point[] { new Point(0.405273f, 0.395833f), new Point(0.429688f, 0.394531f), new Point(0.431641f, 0.389323f), new Point(0.445312f, 0.389323f), new Point(0.441406f, 0.399740f), new Point(0.458984f, 0.399740f), new Point(0.467773f, 0.380208f), new Point(0.462891f, 0.380208f), new Point(0.464844f, 0.373698f), new Point(0.449219f, 0.339844f), new Point(0.431641f, 0.339844f) });
		Territory france = new Territory("Franca", new Point(491f/1024, 243f/768), new Point[] { new Point(0.465820f, 0.372396f), new Point(0.471680f, 0.356771f), new Point(0.482422f, 0.356771f), new Point(0.489258f, 0.335938f), new Point(0.496094f, 0.335938f), new Point(0.525391f, 0.266927f), new Point(0.513672f, 0.236979f), new Point(0.502930f, 0.253906f), new Point(0.508789f, 0.263021f), new Point(0.503906f, 0.277344f), new Point(0.487305f, 0.278646f), new Point(0.476562f, 0.300781f), new Point(0.437500f, 0.300781f), new Point(0.440430f, 0.311198f), new Point(0.448242f, 0.312500f), new Point(0.455078f, 0.325521f), new Point(0.450195f, 0.339844f) });
		Territory italy = new Territory("Italia", new Point(535f/1024, 239f/768), new Point[] { new Point(0.496094f, 0.334635f), new Point(0.503906f, 0.335938f), new Point(0.513672f, 0.359375f), new Point(0.520508f, 0.360677f), new Point(0.529297f, 0.382812f), new Point(0.524414f, 0.393229f), new Point(0.536133f, 0.394531f), new Point(0.541992f, 0.380208f), new Point(0.543945f, 0.382812f), new Point(0.549805f, 0.382812f), new Point(0.539062f, 0.355469f), new Point(0.533203f, 0.355469f), new Point(0.526367f, 0.333333f), new Point(0.530273f, 0.322917f), new Point(0.539062f, 0.322917f), new Point(0.541992f, 0.329427f), new Point(0.553711f, 0.303385f), new Point(0.539062f, 0.266927f), new Point(0.525391f, 0.266927f) });
		Territory poland = new Territory("Polonia", new Point(572f/1024, 204f/768), new Point[] { new Point(0.553711f, 0.302083f), new Point(0.567383f, 0.302083f), new Point(0.585938f, 0.261719f), new Point(0.569336f, 0.222656f), new Point(0.561523f, 0.222656f), new Point(0.556641f, 0.239583f), new Point(0.549805f, 0.239583f), new Point(0.539062f, 0.266927f) });
		Territory romania = new Territory("Romenia", new Point(580f/1024, 267f/768), new Point[] { new Point(0.553711f, 0.302083f), new Point(0.567383f, 0.303385f), new Point(0.592773f, 0.361979f), new Point(0.583984f, 0.363281f), new Point(0.581055f, 0.371094f), new Point(0.586914f, 0.381510f), new Point(0.580078f, 0.394531f), new Point(0.567383f, 0.394531f), new Point(0.560547f, 0.381510f), new Point(0.565430f, 0.368490f), new Point(0.552734f, 0.337240f), new Point(0.542969f, 0.335938f), new Point(0.542969f, 0.329427f) });
		Territory ukraine = new Territory("Ucrania", new Point(604f/1024, 238f/768), new Point[] { new Point(0.568359f, 0.303385f), new Point(0.592773f, 0.361979f), new Point(0.604492f, 0.338542f), new Point(0.596680f, 0.324219f), new Point(0.604492f, 0.304688f), new Point(0.585938f, 0.263021f) });
		Territory sweden = new Territory("Suecia", new Point(540f/1024, 133f/768), new Point[] { new Point(0.534180f, 0.231771f), new Point(0.541016f, 0.217448f), new Point(0.538086f, 0.210938f), new Point(0.543945f, 0.196615f), new Point(0.538086f, 0.183594f), new Point(0.548828f, 0.160156f), new Point(0.564453f, 0.160156f), new Point(0.555664f, 0.182292f), new Point(0.564453f, 0.182292f), new Point(0.557617f, 0.199219f), new Point(0.587891f, 0.199219f), new Point(0.595703f, 0.182292f), new Point(0.568359f, 0.119792f), new Point(0.537109f, 0.119792f), new Point(0.530273f, 0.138021f), new Point(0.524414f, 0.138021f), new Point(0.509766f, 0.175781f), new Point(0.494141f, 0.177083f), new Point(0.479492f, 0.210938f), new Point(0.488281f, 0.231771f), new Point(0.501953f, 0.231771f), new Point(0.508789f, 0.216146f), new Point(0.517578f, 0.216146f), new Point(0.522461f, 0.231771f) });
		Territory unitedKingdom = new Territory("Reino Unido", new Point(461f/1024, 175f/768), new Point[] { new Point(0.434570f, 0.266927f), new Point(0.472656f, 0.266927f), new Point(0.478516f, 0.251302f), new Point(0.470703f, 0.250000f), new Point(0.458008f, 0.220052f), new Point(0.468750f, 0.197917f), new Point(0.461914f, 0.197917f), new Point(0.469727f, 0.183594f), new Point(0.450195f, 0.183594f), new Point(0.439453f, 0.205729f), new Point(0.449219f, 0.207031f), new Point(0.442383f, 0.220052f), new Point(0.451172f, 0.238281f), new Point(0.447266f, 0.247396f), new Point(0.440430f, 0.248698f) });
		Territory brazil = new Territory("Brasil", new Point(291f/1024, 453f/768), new Point[] { new Point(0.247070f, 0.513021f), new Point(0.252930f, 0.522135f), new Point(0.278320f, 0.523438f), new Point(0.286133f, 0.544271f), new Point(0.297852f, 0.544271f), new Point(0.308594f, 0.572917f), new Point(0.327148f, 0.572917f), new Point(0.333984f, 0.588542f), new Point(0.335938f, 0.593750f), new Point(0.317383f, 0.632812f), new Point(0.325195f, 0.649740f), new Point(0.267578f, 0.649740f), new Point(0.228516f, 0.558594f) });
		Territory venezuela = new Territory("Venezuela", new Point(194f/1024, 432f/768), new Point[] { new Point(0.157227f, 0.585938f), new Point(0.188477f, 0.510417f), new Point(0.247070f, 0.513021f), new Point(0.227539f, 0.557292f), new Point(0.191406f, 0.640625f), new Point(0.184570f, 0.621094f), new Point(0.170898f, 0.621094f) });
		Territory peru = new Territory("Peru", new Point(236f/1024, 494f/768), new Point[] { new Point(0.227539f, 0.558594f), new Point(0.191406f, 0.640625f), new Point(0.198242f, 0.654948f), new Point(0.210938f, 0.653646f), new Point(0.226562f, 0.694010f), new Point(0.221680f, 0.704427f), new Point(0.233398f, 0.727865f), new Point(0.267578f, 0.651042f) });
		Territory argentina = new Territory("Argentina", new Point(271f/1024, 548f/768), new Point[] { new Point(0.267578f, 0.649740f), new Point(0.233398f, 0.726562f), new Point(0.228516f, 0.736979f), new Point(0.271484f, 0.839844f), new Point(0.288086f, 0.838542f), new Point(0.275391f, 0.805990f), new Point(0.303711f, 0.738281f), new Point(0.295898f, 0.721354f), new Point(0.324219f, 0.651042f) });
		Territory algeria = new Territory("Argelia", new Point(463f/1024, 375f/768), new Point[] { new Point(0.425781f, 0.432292f), new Point(0.460938f, 0.432292f), new Point(0.467773f, 0.449219f), new Point(0.480469f, 0.449219f), new Point(0.487305f, 0.467448f), new Point(0.526367f, 0.467448f), new Point(0.500000f, 0.527344f), new Point(0.415039f, 0.527344f), new Point(0.399414f, 0.494792f) });
		Territory egypt = new Territory("Egito", new Point(565f/1024, 390f/768), new Point[] { new Point(0.500977f, 0.527344f), new Point(0.534180f, 0.527344f), new Point(0.548828f, 0.561198f), new Point(0.607422f, 0.561198f), new Point(0.579102f, 0.494792f), new Point(0.583008f, 0.485677f), new Point(0.574219f, 0.462240f), new Point(0.529297f, 0.462240f) });
		Territory nigeria = new Territory("Nigeria", new Point(511f/1024, 435f/768), new Point[] { new Point(0.500977f, 0.527344f), new Point(0.534180f, 0.527344f), new Point(0.573242f, 0.621094f), new Point(0.506836f, 0.621094f), new Point(0.491211f, 0.582031f), new Point(0.437500f, 0.582031f), new Point(0.415039f, 0.527344f) });
		Territory somalia = new Territory("Somalia", new Point(618f/1024, 442f/768), new Point[] { new Point(0.549805f, 0.562500f), new Point(0.584961f, 0.647135f), new Point(0.559570f, 0.703125f), new Point(0.602539f, 0.703125f), new Point(0.626953f, 0.647135f), new Point(0.637695f, 0.647135f), new Point(0.656250f, 0.598958f), new Point(0.624023f, 0.598958f), new Point(0.608398f, 0.561198f) });
		Territory angola = new Territory("Angola", new Point(551f/1024, 507f/768), new Point[] { new Point(0.507812f, 0.621094f), new Point(0.573242f, 0.621094f), new Point(0.584961f, 0.648438f), new Point(0.559570f, 0.703125f), new Point(0.515625f, 0.703125f), new Point(0.522461f, 0.684896f), new Point(0.501953f, 0.635417f) });
		Territory southAfrica = new Territory("Africa do Sul", new Point(577f/1024, 568f/768), new Point[] { new Point(0.515625f, 0.704427f), new Point(0.543945f, 0.774740f), new Point(0.583008f, 0.774740f), new Point(0.590820f, 0.755208f), new Point(0.598633f, 0.753906f), new Point(0.611328f, 0.721354f), new Point(0.602539f, 0.704427f) });		
		Territory estonia = new Territory("Estonia", new Point(674f/1024, 132f/768), new Point[] { new Point(0.569336f, 0.119792f), new Point(0.612305f, 0.217448f), new Point(0.690430f, 0.218750f), new Point(0.717773f, 0.154948f), new Point(0.642578f, 0.154948f), new Point(0.634766f, 0.180990f), new Point(0.614258f, 0.180990f), new Point(0.599609f, 0.144531f), new Point(0.612305f, 0.145833f), new Point(0.616211f, 0.154948f), new Point(0.628906f, 0.154948f), new Point(0.617188f, 0.125000f), new Point(0.592773f, 0.125000f), new Point(0.589844f, 0.118490f) });
		Territory latvia = new Territory("Letonia", new Point(659f/1024, 192f/768), new Point[] { new Point(0.595703f, 0.182292f), new Point(0.577148f, 0.221354f), new Point(0.569336f, 0.222656f), new Point(0.595703f, 0.285156f), new Point(0.708008f, 0.283854f), new Point(0.720703f, 0.255208f), new Point(0.708008f, 0.220052f), new Point(0.610352f, 0.218750f) });
		Territory turkey = new Territory("Turquia", new Point(713f/1024, 248f/768), new Point[] { new Point(0.598633f, 0.285156f), new Point(0.752930f, 0.285156f), new Point(0.764648f, 0.313802f), new Point(0.746094f, 0.355469f), new Point(0.666992f, 0.355469f), new Point(0.673828f, 0.343750f), new Point(0.662109f, 0.315104f), new Point(0.651367f, 0.315104f), new Point(0.648438f, 0.307292f), new Point(0.635742f, 0.305990f), new Point(0.630859f, 0.320312f), new Point(0.624023f, 0.304688f), new Point(0.605469f, 0.304688f) });
		Territory russia = new Territory("Russia", new Point(779f/1024, 158f/768), new Point[] { new Point(0.689453f, 0.218750f), new Point(0.707031f, 0.218750f), new Point(0.720703f, 0.255208f), new Point(0.805664f, 0.255208f), new Point(0.848633f, 0.156250f), new Point(0.755859f, 0.154948f), new Point(0.724609f, 0.162760f), new Point(0.729492f, 0.162760f), new Point(0.725586f, 0.170573f), new Point(0.710938f, 0.170573f) });
		Territory siberia = new Territory("Siberia", new Point(894f/1024, 148f/768), new Point[] { new Point(0.805664f, 0.255208f), new Point(0.854492f, 0.144531f), new Point(0.915039f, 0.145833f), new Point(0.929688f, 0.179688f), new Point(0.917969f, 0.180990f), new Point(0.923828f, 0.194010f), new Point(0.917969f, 0.204427f), new Point(0.933594f, 0.240885f), new Point(0.926758f, 0.257812f), new Point(0.916016f, 0.231771f), new Point(0.908203f, 0.231771f), new Point(0.894531f, 0.201823f), new Point(0.885742f, 0.222656f), new Point(0.863281f, 0.222656f), new Point(0.855469f, 0.238281f), new Point(0.868164f, 0.256510f) });
		Territory kazakhstan = new Territory("Cazaquistao", new Point(825f/1024, 219f/768), new Point[] { new Point(0.720703f, 0.256510f), new Point(0.707031f, 0.283854f), new Point(0.753906f, 0.285156f), new Point(0.764648f, 0.313802f), new Point(0.883789f, 0.313802f), new Point(0.897461f, 0.282552f), new Point(0.885742f, 0.255208f) });
		Territory mongolia = new Territory("Mongolia", new Point(856f/1024, 255f/768), new Point[] { new Point(0.786133f, 0.313802f), new Point(0.801758f, 0.354167f), new Point(0.873047f, 0.355469f), new Point(0.883789f, 0.384115f), new Point(0.893555f, 0.368490f), new Point(0.881836f, 0.337240f), new Point(0.887695f, 0.324219f), new Point(0.883789f, 0.315104f) });
		Territory china = new Territory("China", new Point(791f/1024, 291f/768), new Point[] { new Point(0.785156f, 0.315104f), new Point(0.801758f, 0.354167f), new Point(0.853516f, 0.355469f), new Point(0.866211f, 0.385417f), new Point(0.818359f, 0.386719f), new Point(0.792969f, 0.447917f), new Point(0.766602f, 0.449219f), new Point(0.736328f, 0.380208f), new Point(0.764648f, 0.313802f) });
		Territory northKorea = new Territory("Coreia do Norte", new Point(862f/1024, 308f/768), new Point[] { new Point(0.818359f, 0.386719f), new Point(0.867188f, 0.386719f), new Point(0.872070f, 0.401042f), new Point(0.886719f, 0.402344f), new Point(0.891602f, 0.416667f), new Point(0.805664f, 0.417969f) });
		Territory southKorea = new Territory("Coreia do Sul", new Point(866f/1024, 333f/768), new Point[] { new Point(0.805664f, 0.417969f), new Point(0.791992f, 0.447917f), new Point(0.893555f, 0.449219f), new Point(0.900391f, 0.432292f), new Point(0.892578f, 0.417969f) });
		Territory japan = new Territory("Japao", new Point(949f/1024, 274f/768), new Point[] { new Point(0.915039f, 0.283854f), new Point(0.933594f, 0.324219f), new Point(0.930664f, 0.329427f), new Point(0.941406f, 0.352865f), new Point(0.931641f, 0.375000f), new Point(0.923828f, 0.375000f), new Point(0.916992f, 0.393229f), new Point(0.899414f, 0.393229f), new Point(0.908203f, 0.375000f), new Point(0.905273f, 0.368490f), new Point(0.911133f, 0.356771f), new Point(0.916016f, 0.360677f), new Point(0.920898f, 0.343750f), new Point(0.905273f, 0.304688f) });
		Territory syria = new Territory("Siria", new Point(667f/1024, 289f/768), new Point[] { new Point(0.700195f, 0.356771f), new Point(0.691406f, 0.378906f), new Point(0.699219f, 0.397135f), new Point(0.621094f, 0.397135f), new Point(0.621094f, 0.391927f), new Point(0.612305f, 0.390625f), new Point(0.604492f, 0.373698f), new Point(0.613281f, 0.351562f), new Point(0.630859f, 0.352865f), new Point(0.630859f, 0.347656f), new Point(0.644531f, 0.347656f), new Point(0.648438f, 0.356771f) });
		Territory iraq = new Territory("Iraque", new Point(673f/1024, 374f/768), new Point[] { new Point(0.637695f, 0.440104f), new Point(0.664062f, 0.497396f), new Point(0.689453f, 0.497396f), new Point(0.676758f, 0.466146f), new Point(0.685547f, 0.449219f), new Point(0.673828f, 0.421875f), new Point(0.683594f, 0.397135f), new Point(0.655273f, 0.397135f) });
		Territory jordania = new Territory("Jordania", new Point(625f/1024, 345f/768), new Point[] { new Point(0.654297f, 0.398438f), new Point(0.612305f, 0.498698f), new Point(0.607422f, 0.485677f), new Point(0.597656f, 0.485677f), new Point(0.587891f, 0.458333f), new Point(0.600586f, 0.432292f), new Point(0.618164f, 0.432292f), new Point(0.632812f, 0.397135f) });
		Territory saudiArabia = new Territory("Arabia Saudita", new Point(671f/1024, 400f/768), new Point[] { new Point(0.637695f, 0.441406f), new Point(0.664062f, 0.498698f), new Point(0.699219f, 0.497396f), new Point(0.708984f, 0.518229f), new Point(0.682617f, 0.579427f), new Point(0.632812f, 0.579427f), new Point(0.624023f, 0.559896f), new Point(0.630859f, 0.545573f), new Point(0.612305f, 0.498698f) });		
		Territory perth = new Territory("Perth", new Point(803f/1024, 597f/768), new Point[] { new Point(0.738281f, 0.826823f), new Point(0.748047f, 0.847656f), new Point(0.761719f, 0.847656f), new Point(0.768555f, 0.829427f), new Point(0.795898f, 0.828125f), new Point(0.840820f, 0.725260f), new Point(0.833984f, 0.709635f), new Point(0.841797f, 0.699219f), new Point(0.835938f, 0.691406f), new Point(0.818359f, 0.691406f), new Point(0.802734f, 0.729167f), new Point(0.779297f, 0.730469f), new Point(0.770508f, 0.753906f), new Point(0.761719f, 0.753906f), new Point(0.755859f, 0.770833f), new Point(0.763672f, 0.785156f), new Point(0.763672f, 0.790365f), new Point(0.751953f, 0.813802f), new Point(0.745117f, 0.813802f) });
		Territory australia = new Territory("Australia", new Point(869f/1024, 608f/768), new Point[] { new Point(0.796875f, 0.828125f), new Point(0.803711f, 0.847656f), new Point(0.817383f, 0.847656f), new Point(0.826172f, 0.869792f), new Point(0.856445f, 0.869792f), new Point(0.869141f, 0.839844f), new Point(0.879883f, 0.839844f), new Point(0.895508f, 0.803385f), new Point(0.889648f, 0.789062f), new Point(0.897461f, 0.772135f), new Point(0.865234f, 0.696615f), new Point(0.853516f, 0.695312f) });
		Territory newZealand = new Territory("Nova Zelandia", new Point(926f/1024, 645f/768), new Point[] { new Point(0.906250f, 0.781250f), new Point(0.915039f, 0.781250f), new Point(0.919922f, 0.796875f), new Point(0.916016f, 0.800781f), new Point(0.921875f, 0.800781f), new Point(0.927734f, 0.817708f), new Point(0.909180f, 0.859375f), new Point(0.904297f, 0.859375f), new Point(0.895508f, 0.881510f), new Point(0.877930f, 0.880208f), new Point(0.894531f, 0.837240f), new Point(0.901367f, 0.835938f), new Point(0.910156f, 0.815104f), new Point(0.906250f, 0.804688f), new Point(0.911133f, 0.791667f) });
		Territory indonesia = new Territory("Indonesia", new Point(892f/1024, 507f/768), new Point[] { new Point(0.821289f, 0.619792f), new Point(0.831055f, 0.619792f), new Point(0.839844f, 0.644531f), new Point(0.859375f, 0.644531f), new Point(0.866211f, 0.626302f), new Point(0.881836f, 0.626302f), new Point(0.885742f, 0.641927f), new Point(0.903320f, 0.641927f), new Point(0.913086f, 0.666667f), new Point(0.920898f, 0.667969f), new Point(0.930664f, 0.691406f), new Point(0.905273f, 0.691406f), new Point(0.898438f, 0.673177f), new Point(0.884766f, 0.673177f), new Point(0.882812f, 0.682292f), new Point(0.868164f, 0.680990f), new Point(0.864258f, 0.667969f), new Point(0.828125f, 0.666667f), new Point(0.815430f, 0.631510f) });
		Territory iran = new Territory("Ira", new Point(717f/1024, 342f/768), new Point[] { new Point(0.683594f, 0.398438f), new Point(0.699219f, 0.398438f), new Point(0.673828f, 0.421875f), new Point(0.699219f, 0.483073f), new Point(0.715820f, 0.483073f), new Point(0.722656f, 0.497396f), new Point(0.732422f, 0.475260f) });
		Territory pakistan = new Territory("Paquistao", new Point(748f/1024, 326f/768), new Point[] { new Point(0.700195f, 0.355469f), new Point(0.745117f, 0.355469f), new Point(0.736328f, 0.380208f), new Point(0.766602f, 0.449219f), new Point(0.745117f, 0.497396f), new Point(0.722656f, 0.497396f), new Point(0.732422f, 0.475260f), new Point(0.691406f, 0.380208f) });
		Territory india = new Territory("India", new Point(794f/1024, 381f/768), new Point[] { new Point(0.766602f, 0.449219f), new Point(0.827148f, 0.449219f), new Point(0.788086f, 0.540365f), new Point(0.792969f, 0.550781f), new Point(0.778320f, 0.578125f), new Point(0.746094f, 0.497396f) });
		Territory bangladesh = new Territory("Bangladesh", new Point(844f/1024, 380f/768), new Point[] { new Point(0.827148f, 0.449219f), new Point(0.864258f, 0.449219f), new Point(0.837891f, 0.507812f), new Point(0.859375f, 0.557292f), new Point(0.850586f, 0.571615f), new Point(0.858398f, 0.589844f), new Point(0.849609f, 0.605469f), new Point(0.834961f, 0.567708f), new Point(0.838867f, 0.555990f), new Point(0.828125f, 0.528646f), new Point(0.822266f, 0.528646f), new Point(0.808594f, 0.494792f) });
		Territory thailand = new Territory("Tailandia", new Point(891f/1024, 370f/768), new Point[] { new Point(0.862305f, 0.450521f), new Point(0.893555f, 0.449219f), new Point(0.904297f, 0.475260f), new Point(0.896484f, 0.497396f), new Point(0.896484f, 0.498698f), new Point(0.886719f, 0.507812f), new Point(0.874023f, 0.507812f), new Point(0.871094f, 0.502604f), new Point(0.865234f, 0.511719f), new Point(0.879883f, 0.541667f), new Point(0.875977f, 0.548177f), new Point(0.880859f, 0.557292f), new Point(0.874023f, 0.574219f), new Point(0.866211f, 0.574219f), new Point(0.837891f, 0.510417f) });
		
		brazil.addNeighbour(argentina);
		brazil.addNeighbour(peru);
		brazil.addNeighbour(venezuela);
		peru.addNeighbour(argentina);
		peru.addNeighbour(venezuela);
		
		world.continents[0] = new Continent("America do Sul", new Territory[] {
				brazil, argentina,
				peru, venezuela
		}, 2, new Color(0, 104, 58));
		
		mexico.addNeighbour(venezuela);
		mexico.addNeighbour(texas);
		mexico.addNeighbour(california);
		texas.addNeighbour(california);
		texas.addNeighbour(newYork);
		texas.addNeighbour(vancouver);
		texas.addNeighbour(quebec);
		california.addNeighbour(vancouver);
		newYork.addNeighbour(quebec);
		vancouver.addNeighbour(alaska);
		vancouver.addNeighbour(quebec);
		vancouver.addNeighbour(calgary);
		calgary.addNeighbour(alaska);
		calgary.addNeighbour(greenland);
		quebec.addNeighbour(greenland);

		world.continents[1] = new Continent("America do Norte", new Territory[] {
				alaska, calgary, greenland,
				vancouver, quebec,
				california, texas, newYork,
				mexico
		}, 5, new Color(238, 64, 54));
		
		unitedKingdom.addNeighbour(greenland);
		unitedKingdom.addNeighbour(france);
		spain.addNeighbour(france);
		france.addNeighbour(italy);
		france.addNeighbour(sweden);
		italy.addNeighbour(romania);
		italy.addNeighbour(poland);
		italy.addNeighbour(sweden);
		poland.addNeighbour(romania);
		poland.addNeighbour(ukraine);
		romania.addNeighbour(ukraine);
		
		world.continents[2] = new Continent("Europa", new Territory[] {
				unitedKingdom, sweden,
				spain, france, italy,
				poland, romania, ukraine
		}, 5, new Color(43, 56, 143));
						
		siberia.addNeighbour(alaska);
		estonia.addNeighbour(sweden);
		estonia.addNeighbour(latvia);
		estonia.addNeighbour(russia);
		latvia.addNeighbour(poland);
		latvia.addNeighbour(ukraine);
		latvia.addNeighbour(russia);
		latvia.addNeighbour(kazakhstan);
		latvia.addNeighbour(turkey);
		latvia.addNeighbour(sweden);
		russia.addNeighbour(kazakhstan);
		russia.addNeighbour(siberia);
		kazakhstan.addNeighbour(siberia);
		kazakhstan.addNeighbour(mongolia);
		kazakhstan.addNeighbour(china);
		kazakhstan.addNeighbour(turkey);
		kazakhstan.addNeighbour(japan);
		turkey.addNeighbour(ukraine);
		turkey.addNeighbour(china);
		turkey.addNeighbour(pakistan);
		turkey.addNeighbour(syria);
		china.addNeighbour(mongolia);
		china.addNeighbour(northKorea);
		china.addNeighbour(southKorea);
		china.addNeighbour(pakistan);
		mongolia.addNeighbour(japan);
		japan.addNeighbour(northKorea);
		northKorea.addNeighbour(southKorea);
		southKorea.addNeighbour(india);
		southKorea.addNeighbour(bangladesh);
		southKorea.addNeighbour(thailand);
		thailand.addNeighbour(bangladesh);
		bangladesh.addNeighbour(india);
		india.addNeighbour(pakistan);
		pakistan.addNeighbour(syria);
		pakistan.addNeighbour(iran);
		iran.addNeighbour(iraq);
		iran.addNeighbour(syria);
		iraq.addNeighbour(syria);
		iraq.addNeighbour(jordania);
		iraq.addNeighbour(saudiArabia);
		syria.addNeighbour(jordania);
		jordania.addNeighbour(saudiArabia);

		world.continents[3] = new Continent("Asia", new Territory[] {
				estonia, russia, siberia,
				latvia, kazakhstan,
				turkey, mongolia, japan,
				syria, pakistan,
				china, northKorea, southKorea,
				iraq, iran, india, bangladesh,
				thailand, saudiArabia,
				jordania
		}, 7, new Color(246, 146, 30));
		
		algeria.addNeighbour(italy);
		algeria.addNeighbour(spain);
		algeria.addNeighbour(egypt);
		algeria.addNeighbour(nigeria);
		nigeria.addNeighbour(brazil);
		nigeria.addNeighbour(egypt);
		nigeria.addNeighbour(somalia);
		nigeria.addNeighbour(angola);
		egypt.addNeighbour(romania);
		egypt.addNeighbour(somalia);
		egypt.addNeighbour(jordania);
		somalia.addNeighbour(saudiArabia);
		somalia.addNeighbour(angola);
		somalia.addNeighbour(southAfrica);
		angola.addNeighbour(southAfrica);

		world.continents[4] = new Continent("Africa", new Territory[] {
				algeria, egypt,
				nigeria, somalia,
				angola, southAfrica
		}, 3, new Color(101, 45, 144));
		
		indonesia.addNeighbour(india);
		indonesia.addNeighbour(bangladesh);
		indonesia.addNeighbour(australia);
		indonesia.addNeighbour(newZealand);
		newZealand.addNeighbour(australia);
		australia.addNeighbour(perth);

		world.continents[5] = new Continent("Oceania", new Territory[] {
				indonesia, newZealand,
				australia, perth
		}, 2, new Color(38, 169, 224));
				
		return world;
	}
	
	public int getTerritoryCount() {
		int count = 0;
		for (int i = 0; i < continents.length; ++i) {
			count += continents[i].getTerritoryCount();
		}
		return count;
	}
	
	public Territory findTerritory(String name) {
		for (Continent c : continents) {
			Territory t = c.findTerritory(name);
			if (t != null) {
				return t;
			}
		}
		System.out.printf("Função findTerritory retornou null para %s\n", name);
		return null;
	}

	
	public Continent findContinent(String name) {
		for (Continent c : continents) {
			if (c.getName() == name) {
				return c;
			}
		}
		return null;
	}
}
