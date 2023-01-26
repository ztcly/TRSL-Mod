package ztcly.translation.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import org.apache.logging.log4j.Logger;
import ztcly.translation.TranslationMod;
import ztcly.translation.youdao.Output;
import ztcly.translation.youdao.YouDao;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class TrslCommand extends CommandBase {
    Logger logger = TranslationMod.logger;

    @Override
    public List<String> getTabCompletions(MinecraftServer server,ICommandSender sender, String[] args,@Nullable BlockPos pos)//用于指令输入时的Tab自动补全
    {
        if (args.length == 1)
        {
            String[] names = {"cte","c2e","etc","e2c","ltc","l2c"};
            return CommandBase.getListOfStringsMatchingLastWord(args, names);
        }
        String[] error={"."};
        return CommandBase.getListOfStringsMatchingLastWord(args, error);
    }

    @Override
    public String getName() {
        //指令的名字
        return "trsl";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        //返回指令的用法，TranslationKey
        return "commands.trsl.usage";
    }


    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 2)
        {
            logger.info("WrongUsage:"+args[0]);
            throw new WrongUsageException("commands.trsl.usage");
        }
        Output output = null;
        if(args[0].equals("cte")||args[0].equals("c2e")){
            logger.info("chinese to english:"+args[1]);
            output = YouDao.onlineTranslation(args[1],"zh-CHS","en");
        }else if(args[0].equals("etc")||args[0].equals("e2c")){
            logger.info("english to chinese:"+args[1]);
            output = YouDao.onlineTranslation(args[1],"en","zh-CHS");
        }else if(args[0].equals("ltc")||args[0].equals("l2c")){
            logger.info("latin to chinese"+args[1]);
            output = YouDao.onlineTranslation(args[1],"la","zh-CHS");
        }else{
            logger.info("WrongUsage:"+args[0]);
            throw new WrongUsageException("commands.trsl.usage");
        }
        if(output==null){
            logger.error("Output is null");
            sender.sendMessage(new TextComponentTranslation("commands.trsl.error"));
        }else if(output.errorCode.equals("108")||output.errorCode.equals("111")||output.errorCode.equals("112")||output.errorCode.equals("202")){
            //错误码
            // 108	应用ID无效，注册账号，登录后台创建应用和实例并完成绑定，可获得应用ID和应用密钥等信息
            // 111	开发者账号无效
            // 112	请求服务无效
            logger.error("Wrong APP Config,Error Code:"+output.errorCode.toString());
            sender.sendMessage(new TextComponentTranslation("commands.trsl.errorcode.config",output.errorCode.toString()));
        }else if(output.errorCode.equals("0")){
            logger.info("TranslationOutPutStart");
            sender.sendMessage(new TextComponentTranslation("commands.trsl.translation"));
            logger.info("query:"+output.query+" translation:"+output.translation.toString());
            sender.sendMessage(new TextComponentString("["+output.query+"]:"+output.translation.toString()));
            if(output.basic!=null&&output.isWord){//返回的信息中含有解释并且源文本是个词语才会输出解释
                String explains = output.getExplains();
                if(explains.equals("NULL")){//释义为NULL，正常不会出现
                    sender.sendMessage(new TextComponentTranslation("commands.trsl.explains.null"));
                }else if(!explains.equals("NA")){//释义为NA，即释义不可用
                    sender.sendMessage(new TextComponentTranslation("commands.trsl.explains"));
                    sender.sendMessage(new TextComponentString(output.getExplains()));
                }
                String exam_type = output.getExamType();
                if(exam_type.equals("NULL")){//同理，考试类型
                    sender.sendMessage(new TextComponentTranslation("commands.trsl.examtype.null"));
                }else if(!exam_type.equals("NA")){//同理
                    sender.sendMessage(new TextComponentTranslation("commands.trsl.examtype"));
                    sender.sendMessage(new TextComponentString(output.getExamType()));
                }
            }
        }else{
            logger.error("Output Error,Error Code:"+output.errorCode.toString());
            sender.sendMessage(new TextComponentTranslation("commands.trsl.errorcode.unknown",output.errorCode.toString()));
        }


    }


    @Override
    public int getRequiredPermissionLevel() {
        return 0; //  任何人都能用 无需权限
    }
}
