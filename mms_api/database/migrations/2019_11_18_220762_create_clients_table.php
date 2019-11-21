<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateClientsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('clients', function (Blueprint $table) {
            $table->bigIncrements('id');
            $table->string('profession');
            $table->string('employeur');
            $table->unsignedBigInteger('user_id');
            $table->unsignedBigInteger('marchand_id');

            $table->foreign('user_id')
                ->references('id')
                ->on('users')
                ->ondelete('cascade');
    
            $table->foreign('marchand_id')
                ->references('id')
                ->on('marchands')
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
        Schema::dropIfExists('clients');
    }
}
