<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateMarchandsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('marchands', function (Blueprint $table) {
            $table->bigIncrements('id');
            $table->string('matricule');
            $table->string('credit_virtuel');
            $table->string('commission');
                        
            $table->unsignedBigInteger('super_marchand_id');

            $table->foreign('super_marchand_id')
                ->references('id')
                ->on('super_marchands')
                ->ondelete('cascade');
                
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('marchands');
    }
}
